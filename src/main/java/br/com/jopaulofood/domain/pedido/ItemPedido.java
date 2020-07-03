package br.com.jopaulofood.domain.pedido;

import java.math.BigDecimal;

import br.com.jopaulofood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

	private Integer id;
	
	private ItemCardapio itemCardapio;
	
	private Integer quantidade;
	
	private String observacoes;
	
	private BigDecimal preco;
}
