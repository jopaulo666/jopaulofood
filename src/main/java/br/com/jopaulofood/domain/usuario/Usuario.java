package br.com.jopaulofood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.jopaulofood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class Usuario implements Serializable {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Nome obrigatório")
	@Size(max = 80, message = "O nome não pode ter mais de 80 caracteres")
	private String nome;
	
	@NotBlank(message = "E-mail obrigatório")
	@Size(max = 60, message = "O e-mail não pode ter mais de 60 caracteres")
	@Email(message = "E-mail inválido")
	private String email;
	
	@NotBlank(message = "Telefone obrigatório")
	@Pattern(regexp = "^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$", message = "Telefone inválido")
	@Column(length = 15, nullable = false)
	private String telefone;
	
	@NotBlank(message = "Senha obrigatória")
	@Size(max = 80, message = "A senha não pode ter mais de 80 caracteres")
	private String senha;
	
	public void encryptPassord() {
		this.senha = StringUtils.encrypt(this.senha);
	}
}
