package com.fama.agenda.repository.filter;

import java.io.Serializable;

import com.fama.agenda.model.Grupo;

/**
 * @author Elton Jhony R. Oliveira
 */

public class UsuarioFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Grupo grupos;
	
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Grupo getGrupos() {
		return grupos;
	}
	
	public void setGrupos(Grupo grupos) {
		this.grupos = grupos;
	}

}
