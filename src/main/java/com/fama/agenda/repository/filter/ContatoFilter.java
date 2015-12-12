package com.fama.agenda.repository.filter;

import java.io.Serializable;

import com.fama.agenda.model.TipoPessoa;

/**
 * @author Elton Jhony R. Oliveira
 */

public class ContatoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String apelido;
	private TipoPessoa[] tipoPessoa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public TipoPessoa[] getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa[] tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

}
