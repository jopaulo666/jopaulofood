package br.com.jopaulofood.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jopaulofood.domain.cliente.Cliente;
import br.com.jopaulofood.domain.cliente.ClienteRepository;
import br.com.jopaulofood.domain.restaurante.Restaurante;
import br.com.jopaulofood.domain.restaurante.RestauranteRepository;
import br.com.jopaulofood.domain.restaurante.SearchFilter;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
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
		return restauranteRepository.findAll();
	}
}
