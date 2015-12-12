package com.fama.agenda.model;

public enum Visibilidade {
	
	RESTRITA("Restrita"),
	PUBLICA("Publica");
	
	private Visibilidade(String descricao) {
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
