package br.com.jopaulofood.domain.restaurante;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.jopaulofood.domain.usuario.Usuario;
import br.com.jopaulofood.infrastructure.web.validator.UploadConstraint;
import br.com.jopaulofood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario {

	@NotBlank(message = "CNPJ obrigatório")
	@Pattern(regexp = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}", message = "CPF inválido")
	@Column(length = 18, nullable = false)
	private String cnpj;
	
	@Size(max = 80)
	private String logotipo;
	
	@UploadConstraint(acceptedTypes = {FileType.PNG, FileType.JPG}, message = "Arquivo de imagem inválido")
	private transient MultipartFile logotipoFile;
	
	@NotNull(message = "Taxa de entrega obrigatória")
	@Min(0)
	@Max(99)
	private BigDecimal taxaEntrega;
	
	@NotNull(message = "Tempo de entrega obrigatório")
	@Min(0)
	@Max(120)
	private Integer tempoEntregaBase;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
				name = "restaurante_has_categoria",
				joinColumns = @JoinColumn(name = "restaurante_id"),
				inverseJoinColumns = @JoinColumn(name = "categoria_restaurante")
			)
	@Size(min = 1, message = "Escolha ao menos uma categoria de comida")
	@ToString.Exclude
	private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
	
	public void setLogotipoFileName() {
		if (getId() == null) {
			throw new IllegalStateException("É preciso gravar o produto");
		}
		this.logotipo = String.format("%04d-log.%s", getId(), getId(), FileType.of(logotipoFile.getContentType()).getExtension());
	}
	
}
