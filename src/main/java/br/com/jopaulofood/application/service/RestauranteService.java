package br.com.jopaulofood.application.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jopaulofood.domain.cliente.Cliente;
import br.com.jopaulofood.domain.cliente.ClienteRepository;
import br.com.jopaulofood.domain.restaurante.ItemCardapio;
import br.com.jopaulofood.domain.restaurante.ItemCardapioRepository;
import br.com.jopaulofood.domain.restaurante.Restaurante;
import br.com.jopaulofood.domain.restaurante.RestauranteComparator;
import br.com.jopaulofood.domain.restaurante.RestauranteRepository;
import br.com.jopaulofood.domain.restaurante.SearchFilter;
import br.com.jopaulofood.domain.restaurante.SearchFilter.SearchType;
import br.com.jopaulofood.util.SecurityUtils;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private ImageService imageService;

	@Transactional
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {		
		if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("E-mail já cadastrado");
		}
		
		if (restaurante.getId() != null) {
			Restaurante restaurantedb = restauranteRepository.findById(restaurante.getId()).orElseThrow();
			restaurante.setSenha(restaurantedb.getSenha());
			restaurante.setLogotipo(restaurantedb.getLogotipo());
			restauranteRepository.save(restaurante);
		} else {
			restaurante.encryptPassord();
			restaurante = restauranteRepository.save(restaurante);
			restaurante.setLogotipoFileName();
			imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}
		
	}
	
	private boolean validateEmail(String email, Integer id) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente != null) {
			return false;
		}
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);		
		if (restaurante != null) {
			if (id == null) {
				return false;
			}
			if (!restaurante.getId().equals(id)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Restaurante> search(SearchFilter filter){
		List<Restaurante> restaurantes;				
		if (filter.getSearchType() == SearchType.Texto) {
			restaurantes = restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());
		} else if (filter.getSearchType() == SearchType.Categoria) {
			restaurantes = restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
		} else {
			throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + "não suportado");
		}
		
		Iterator<Restaurante> iterator = restaurantes.iterator();				
		while (iterator.hasNext()) {
			Restaurante restaurante = iterator.next();
			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();
			
			if (filter.isEntregaGratis() && taxaEntrega > 0) {
				iterator.remove();
			}			
		}		
		RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator);
		
		return restaurantes; 
	}
	
	@Transactional
	public void saveItemCardapio(ItemCardapio itemCardapio) {
		itemCardapio = itemCardapioRepository.save(itemCardapio);
		itemCardapio.setImagemFileName();
		imageService.uploadComida(itemCardapio.getImagemFile(), itemCardapio.getImagem());
	}
}
