package br.com.jopaulofood.domain.cliente;

import javax.persistence.Entity;

import br.com.jopaulofood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Cliente extends Usuario {

	private String cpf;
	private String cep;
}
