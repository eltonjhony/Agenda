package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.fama.agenda.model.Departamento;

/**
 * @author Elton Jhony R. Oliveira
 */

public class Departamentos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	public Departamento porId(Long id) {
		return this.manager.find(Departamento.class, id);
	}
	
	public List<Departamento> raizes() {
		return manager.createQuery("from Departamento where departamentoPai is null",
				Departamento.class).getResultList();
	}
	
	public List<Departamento> todos() {
		return manager.createQuery("from Departamento where departamentoPai != null",
				Departamento.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Departamento> subdepartamentosDe(Departamento departamentoPai) {
		try {
			return manager
					.createQuery("from Departamento where departamentoPai = :raiz")
					.setParameter("raiz", departamentoPai).getResultList();
		} catch (NullPointerException ex) {
			return todos();
		}
	}
	
	public Departamento salvar(Departamento dep) {
		return manager.merge(dep);
	}

}
