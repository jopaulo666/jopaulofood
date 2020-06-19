package br.com.jopaulofood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.jopaulofood.infrastructure.web.validator.UploadConstraint;
import br.com.jopaulofood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_cardapio")
public class ItemCardapio  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Nome obrigatório")
	@Size(max = 50)
	private String nome;
	
	@NotBlank(message = "Categoria obrigatória")
	@Size(max = 25)
	private String categoria;
	
	@NotBlank(message = "Descrição obrigatório")
	@Size(max = 80)
	private String descricao;
	
	@Size(max = 50)
	private String imagem;
	
	@NotNull(message = "Preço obrigatório")
	@Min(0)
	private BigDecimal preco;
	
	@NotNull
	private boolean destaque;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;
	
	@UploadConstraint(acceptedTypes = {FileType.PNG, FileType.JPG}, message = "Arquivo de imagem inválido. Use somente arquivos com extensão JPEG ou PNG")
	private transient MultipartFile imagemFile;
	
	public void setImagemFileName() {
		if (getId() == null) {
			throw new IllegalStateException("É preciso gravar o produto");
		}
		this.imagem = String.format("%04d-comida.%s", getId(), getId(), FileType.of(imagemFile.getContentType()).getExtension());
	}
}
