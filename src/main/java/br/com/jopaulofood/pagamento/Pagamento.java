package br.com.jopaulofood.pagamento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.jopaulofood.domain.pedido.Pedido;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "pagamento")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable{

	@Id
	private Integer id;
	
	@NotNull
	@OneToOne
	@MapsId
	private Pedido pedido;
	
	@NotNull
	private LocalDateTime data;
	
	@Column(name = "num_cartao_final")
	@NotNull
//	@Size(min = 4, max = 4)
	private String numCartaoFinal;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private BandeiraCartao bandeiraCartao;
	
	public void definirNumeroIsBandeira(String numCartao) {
		numCartaoFinal = numCartao.substring(12);
		bandeiraCartao = getBandeira(numCartao);
	}
	//TODO: resolver 
	private BandeiraCartao getBandeira(String numCartao) {
		if (numCartao.startsWith("0000")) {
			return BandeiraCartao.VISA;
		} else if (numCartao.startsWith("1111")){
			return BandeiraCartao.MASTERCARD;
		} else if (numCartao.startsWith("2222")){
			return BandeiraCartao.MAESTRO;
		} else if (numCartao.startsWith("3333")){
			return BandeiraCartao.ELO;
		} else if (numCartao.startsWith("4444")){
			return BandeiraCartao.AMERICAN;
		} else if (numCartao.startsWith("5555")){
			return BandeiraCartao.HIPERCARD;
		} else {
			return BandeiraCartao.OUTRO;			
		}
	} 

}
