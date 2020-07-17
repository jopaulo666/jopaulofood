package br.com.jopaulofood.pagamento;

public enum StatusPagamento {

	Autorizado("Autorizado"),
	NaoAutorizado("Pagamento não autorizado pela instituição financeira"),
	CartaoInvalido("Cartão inválido ou bloqueado");
	
	String descricao;

	StatusPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
