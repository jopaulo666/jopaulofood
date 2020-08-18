package br.com.jopaulofood.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.jopaulofood.domain.restaurante.CategoriaRestaurante;
import br.com.jopaulofood.domain.restaurante.CategoriaRestauranteRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepositoryTest {
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;

	@Test
	public void testInsertAndDelete() throws Exception {
		assertThat(categoriaRestauranteRepository).isNotNull();
		
		CategoriaRestaurante categoriaRestaurante = new CategoriaRestaurante();
		categoriaRestaurante.setNome("Chinesa");
		categoriaRestaurante.setImagem("chinesa.png");
		categoriaRestauranteRepository.saveAndFlush(categoriaRestaurante);
		
		assertThat(categoriaRestaurante.getId()).isNotNull();
		
		CategoriaRestaurante categoriaRestaurante2 = categoriaRestauranteRepository.findById(categoriaRestaurante.getId())
				.orElseThrow(NoSuchElementException::new);
		assertThat(categoriaRestaurante.getNome()).isEqualTo(categoriaRestaurante2.getNome());
		
		assertThat(categoriaRestauranteRepository.findAll()).hasSize(7);
		
		categoriaRestauranteRepository.delete(categoriaRestaurante);
		
		assertThat(categoriaRestauranteRepository.findAll()).hasSize(6);
	}
}
