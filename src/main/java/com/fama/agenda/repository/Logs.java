package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fama.agenda.model.Log;
import com.fama.agenda.repository.filter.LogFilter;

public class Logs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Log> todos() {
		return this.manager.createQuery("from Log", Log.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Log> filtrados(LogFilter filtro) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Log.class);

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("l.dataOperacao", filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("l.dataOperacao", filtro.getDataAte()));
		}

		return criteria.list();
	}

}
