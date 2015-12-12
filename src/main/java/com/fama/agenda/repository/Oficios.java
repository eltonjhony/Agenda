package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.fama.agenda.model.Oficio;
import com.fama.agenda.model.UsoImagem;
import com.fama.agenda.util.jpa.Transactional;

public class Oficios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Oficio guardar(Oficio oficio) {
		return this.manager.merge(oficio);
	}

	@SuppressWarnings("unchecked")
	public List<Oficio> buscarNumeroUltimoRegistro() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Oficio.class);

		criteria.addOrder(Order.desc("id"));
		return criteria.setMaxResults(1).list();
	}

	public List<Oficio> todos() {
		return this.manager.createQuery("from Oficio order by id desc",
				Oficio.class).getResultList();
	}
	
	public List<UsoImagem> todas() {
		return this.manager.createQuery("from UsoImagem u where u.op=1 order by u.id desc",
				UsoImagem.class).getResultList();
	}

	public Oficio porId(Long id) {
		return this.manager.find(Oficio.class, id);
	}
	
	public UsoImagem byId(Long id) {
		return this.manager.find(UsoImagem.class, id);
	}

	@Transactional
	public UsoImagem guardarImagem(UsoImagem usoImagem) {
		return this.manager.merge(usoImagem);
	}

	@Transactional
	public void renderizar(List<Long> ids) {
		while (!ids.isEmpty()) {
			Oficio temp = this.manager.find(Oficio.class, ids.get(0));
			this.manager.remove(temp);
			ids.remove(0);
			
		}

	}

}
