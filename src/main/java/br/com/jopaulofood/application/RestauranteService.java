package br.com.jopaulofood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jopaulofood.domain.restaurante.Restaurante;
import br.com.jopaulofood.domain.restaurante.RestauranteRepository;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ImageService imageService;

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
}
