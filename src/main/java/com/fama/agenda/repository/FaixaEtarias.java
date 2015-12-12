package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fama.agenda.model.Faixa;

/**
 * @author Elton Jhony R. Oliveira
 */

public class FaixaEtarias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Faixa> todos() {

		Session session = manager.unwrap(Session.class);
		return session
				.createCriteria(Faixa.class, "f")
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("f.id").as("id"))
								.add(Projections.property("f.descricao"),
										"descricao"))
				.setResultTransformer(
						new AliasToBeanResultTransformer(Faixa.class)).list();

	}

	public Faixa porId(Long id) {
		return this.manager.find(Faixa.class, id);
	}

}
