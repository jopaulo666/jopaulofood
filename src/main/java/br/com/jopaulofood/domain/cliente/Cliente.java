package br.com.jopaulofood.domain.cliente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.jopaulofood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {
	
//	@CPF
	@NotBlank(message = "CPF obrigatório")
	@Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}", message = "CPF inválido")
	@Column(length = 14, nullable = false)
	private String cpf;
	
	@NotBlank(message = "CEP obrigatório")
	@Pattern(regexp = "[0-9]{5}\\-?[0-9]{3}", message = "CEP inválido")
	@Column(length = 9, nullable = false)
	private String cep;
}
