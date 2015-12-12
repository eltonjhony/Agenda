package com.fama.agenda.service;

import java.io.Serializable;
import java.util.Calendar;

import javax.inject.Inject;

import com.fama.agenda.model.Contato;
import com.fama.agenda.repository.Contatos;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class CadastroContatoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Contatos contatos;

	@Transactional
	public Contato salvar(Contato contato) {

		contato.setUsuario(new Seguranca().getUsuarioAtivo());
		
		Calendar data = Calendar.getInstance();
		data.add(Calendar.HOUR_OF_DAY, -2);
		contato.setDataCriacao(data.getTime());

		return this.contatos.guardar(contato);
	}

	

}
