package br.com.jopaulofood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {

	public Restaurante findByEmail(String email);
	
	public List<Restaurante> findByNomeIgnoreCaseContaining(String nome); // retorna restaurante por parte do nome iguinorando maíscula
	
	public List<Restaurante> findByCategorias_Id(Integer categoriaId);
}
