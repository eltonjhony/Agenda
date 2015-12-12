package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fama.agenda.model.Log;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.filter.UsuarioFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.service.NegocioException;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public Usuario guardar(Usuario usuario) {
		return this.manager.merge(usuario);
	}

	public Usuario porEmail(String email) {
		try {
			return manager
					.createQuery("from Usuario where lower(email) = :email",
							Usuario.class)
					.setParameter("email", email.toLowerCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {

		Session session = this.manager.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		if (filtro.getGrupos() != null && filtro.getGrupos().getId() != null) {
			criteria.createAlias("grupos", "g");
			criteria.add(Restrictions.like("g.descricao", filtro.getGrupos()
					.getDescricao()));
		}

		return criteria.list();
	}

	@Transactional
	public Usuario excluir(Usuario usuarioSelecionado) {

		Usuario temp = null;

		try {
			temp = this.manager.find(Usuario.class, usuarioSelecionado.getId());
			this.manager.remove(temp);
			Log log = new Log();
			Usuario usuario = new Seguranca().getUsuarioAtivo();
			log.setDataOperacao(new Date());
			log.setDescricao("Usuario excluido: "
					+ usuarioSelecionado.getNomeUsuario());
			log.setUsuario(usuario);
			this.manager.merge(log);
			this.manager.flush();
		} catch (PersistenceException ex) {
			throw new NegocioException(
					"O usuário que você deseja excluir possui vínculos com outras operações do sistema."
							+ " Não é permitida a remoção!");
		}

		return temp;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> todos() {

		Session session = manager.unwrap(Session.class);

		return session
				.createCriteria(Usuario.class, "u")
				.setProjection(
						Projections.projectionList()
								.add(Projections.property("u.id").as("id"))
								.add(Projections.property("u.nome").as("nome"))
								.add(Projections.property("u.email").as("email")))
				.setResultTransformer(
						new AliasToBeanResultTransformer(Usuario.class)).list();
	}

}
