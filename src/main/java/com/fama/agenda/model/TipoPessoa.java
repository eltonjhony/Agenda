package com.fama.agenda.model;

/**
 * @author Elton Jhony R. Oliveira
 */

public enum TipoPessoa {
	
	FISICA("Pessoa Física"),
	JURIDICA("Pessoa Jurídica");
	
	TipoPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
