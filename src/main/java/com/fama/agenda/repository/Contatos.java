package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fama.agenda.model.Contato;
import com.fama.agenda.model.FormaContato;
import com.fama.agenda.model.Log;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.filter.ContatoFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.service.NegocioException;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class Contatos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Contato guardar(Contato contato) {
		return this.manager.merge(contato);
	}

	@SuppressWarnings("unchecked")
	public List<Contato> todos() {

		Session session = manager.unwrap(Session.class);
		return session
				.createCriteria(Contato.class, "c")
				.setProjection(
						Projections.projectionList()
								.add(Projections.property("c.id").as("id"))
								.add(Projections.property("c.nome").as("nome")))
				.setResultTransformer(
						new AliasToBeanResultTransformer(Contato.class)).list();

	}

	@SuppressWarnings("unchecked")
	public List<Contato> filtrados(ContatoFilter filtro) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session
				.createCriteria(Contato.class, "c")
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("c.id").as("id"))
								.add(Projections.property("c.nome").as("nome"))
								.add(Projections.property("c.apelido").as(
										"apelido"))
								.add(Projections.property("c.tipo").as("tipo")))
				.setResultTransformer(
						new AliasToBeanResultTransformer(Contato.class));

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getApelido())) {
			criteria.add(Restrictions.ilike("apelido", filtro.getApelido(),
					MatchMode.ANYWHERE));
		}

		if (filtro.getTipoPessoa().length > 0) {
			criteria.add(Restrictions.in("tipo", filtro.getTipoPessoa()));
		}

		return criteria.list();
	}

	public Contato porId(Long id) {
		return manager.find(Contato.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<FormaContato> porContato(Long id) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(FormaContato.class);

		criteria.createAlias("contato", "c");
		criteria.add(Restrictions.eq("c.id", id));

		return criteria.list();
	}

	@Transactional
	public Contato excluir(Contato contatoSelecionado) {

		Contato temp = null;
		try {
			temp = this.manager.find(Contato.class, contatoSelecionado.getId());
			this.manager.remove(temp);
			Log log = new Log();
			Usuario usuario = new Seguranca().getUsuarioAtivo();
			log.setDataOperacao(new Date());
			log.setDescricao("Contato excluido: "
					+ contatoSelecionado.getNome());
			log.setUsuario(usuario);
			this.manager.merge(log);
			manager.flush();
		} catch (PersistenceException ex) {
			throw new NegocioException(
					"O contato que você deseja excluir possui vínculos com outras operações do sistema."
							+ " Não é permitida a remoção!");
		}

		return temp;
	}

	@SuppressWarnings("unchecked")
	public List<Contato> porFormaContato(FormaContato formaContatoSelecionado) {

		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(FormaContato.class);

		criteria.createAlias("contato", "id");
		criteria.add(Restrictions.eq("contato.id", formaContatoSelecionado
				.getContato().getId()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Contato> Relatoriofiltrados(ContatoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Contato.class, "c")
				.setFetchMode("formaContatos", FetchMode.JOIN);

	
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getApelido())) {
			criteria.add(Restrictions.ilike("c.apelido", filtro.getApelido(),
					MatchMode.ANYWHERE));
		}

		if (filtro.getTipoPessoa().length > 0) {
			criteria.add(Restrictions.in("c.tipo", filtro.getTipoPessoa()));
		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

}
