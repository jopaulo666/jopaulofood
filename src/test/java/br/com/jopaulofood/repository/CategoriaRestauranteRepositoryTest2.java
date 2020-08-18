package br.com.jopaulofood.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.jopaulofood.domain.restaurante.CategoriaRestaurante;
import br.com.jopaulofood.domain.restaurante.CategoriaRestauranteRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepositoryTest2 {
	
	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void testInsertAndDelete() throws Exception {
		assertThat(testEntityManager).isNotNull();
		
		CategoriaRestaurante categoriaRestaurante = new CategoriaRestaurante();
		categoriaRestaurante.setNome("Chinesa");
		categoriaRestaurante.setImagem("chinesa.png");
		testEntityManager.persistAndFlush(categoriaRestaurante);
		
		assertThat(categoriaRestaurante.getId()).isNotNull();
		
		CategoriaRestaurante categoriaRestaurante2 = testEntityManager.find(CategoriaRestaurante.class, categoriaRestaurante.getId());
		assertThat(categoriaRestaurante.getNome()).isEqualTo(categoriaRestaurante2.getNome());
		
		
		testEntityManager.remove(categoriaRestaurante);
		
		assertThat(testEntityManager.find(CategoriaRestaurante.class, categoriaRestaurante.getId())).isNull();
	}
}
