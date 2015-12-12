package com.fama.agenda.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fama.agenda.model.Recado;
import com.fama.agenda.util.jpa.Transactional;

public class Recados implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void guardar(Recado recado) {
		this.manager.merge(recado);
	}

}
