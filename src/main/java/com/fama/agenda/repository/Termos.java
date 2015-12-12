package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fama.agenda.model.Adesao;
import com.fama.agenda.util.jpa.Transactional;

public class Termos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Adesao guardar(Adesao adesao) {
		return this.manager.merge(adesao);
	}

	public List<Adesao> todos() {
		return this.manager.createQuery("from Adesao order by id desc", Adesao.class)
				.getResultList();
	}

	public Adesao porId(Long id) {
		return this.manager.find(Adesao.class, id);
	}

}
