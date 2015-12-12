package com.fama.agenda.service;

/**
 * @author Elton Jhony R. Oliveira
 */

public class NegocioException extends RuntimeException {
	

	private static final long serialVersionUID = 1L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}

}
