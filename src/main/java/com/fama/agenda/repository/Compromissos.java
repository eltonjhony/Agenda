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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.Departamento;
import com.fama.agenda.model.Grupo;
import com.fama.agenda.model.Log;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.model.Visibilidade;
import com.fama.agenda.repository.filter.CompromissoFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.service.NegocioException;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class Compromissos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Compromisso porId(Long id) {
		return this.manager.find(Compromisso.class, id);
	}

	public Compromisso guardar(Compromisso compromisso) {
		return this.manager.merge(compromisso);
	}

	public List<Compromisso> todos() {
		return this.manager
				.createQuery("from Compromisso c", Compromisso.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Compromisso> filtrados(CompromissoFilter filtro) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Compromisso.class, "c")
				.setFetchMode("departamento", FetchMode.JOIN) // Declara que o
				.setFetchMode("subDepartamento", FetchMode.JOIN); // Join será
																	// Fetch
																	// resolve
																	// problema
																	// do
																	// n+1...

		// Projeção para serem retornados somente os campos necessários..
		ProjectionList projectionList = Projections.projectionList().create();
		projectionList
				.add(Projections.property("c.id").as("id"))
				.add(Projections.property("c.dataCompromisso").as(
						"dataCompromisso"))
				.add(Projections.property("c.descricao").as("descricao"))
				.add(Projections.property("c.horario").as("horario"));

		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(
				Compromisso.class));

		if (StringUtils.isNotBlank(filtro.getVisibilidade())) {

			Visibilidade visivel = null;
			if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")) {
				visivel = Visibilidade.RESTRITA;
			} else {
				visivel = Visibilidade.PUBLICA;
			}

			criteria.add(Restrictions.like("c.visibilidade", visivel));
		}

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("c.dataCompromisso",
					filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("c.dataCompromisso",
					filtro.getDataAte()));
		}

		if (filtro.getDepartamentoPai() != null) {
			criteria.createAlias("c.departamento", "dep");
			criteria.add(Restrictions.eq("dep.id", filtro.getDepartamentoPai()
					.getId()));
		}

		if (filtro.getSubdepartamento() != null) {
			criteria.createAlias("c.subDepartamento", "subDep");
			criteria.add(Restrictions.eq("subDep.id", filtro
					.getSubdepartamento().getId()));
		}

		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			criteria.add(Restrictions.ilike("c.descricao",
					filtro.getDescricao(), MatchMode.ANYWHERE));
		}

		return criteria.list();

	}

	@Transactional
	public void excluir(Compromisso compromisso) {
		try {
			Compromisso temp = this.manager.find(Compromisso.class,
					compromisso.getId());
			this.manager.remove(temp);

			// Grava na tabela log_exclusão o registro que está para ser
			// excluído...
			Log log = new Log();
			Usuario usuario = new Seguranca().getUsuarioAtivo();
			log.setDataOperacao(new Date());
			log.setDescricao("Compromisso excluido: "
					+ compromisso.getDescricao());
			log.setUsuario(usuario);
			this.manager.merge(log);

			this.manager.flush();
		} catch (PersistenceException ex) {
			throw new NegocioException(
					"O compromisso que você deseja excluir possui vínculos com outras operações do sistema."
							+ " Não é permitida a remoção!");
		}
	}

	public Departamento porDepartamento(String descricao) {

		return this.manager
				.createQuery("from Departamento where descricao = :desc",
						Departamento.class).setParameter("desc", descricao)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Compromisso> filtradosRelatorio(CompromissoFilter filtro) {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Compromisso.class, "c")
				.setFetchMode("c.formaContato", FetchMode.JOIN)
				.setFetchMode("c.contato", FetchMode.JOIN);

		if (StringUtils.isNotBlank(filtro.getVisibilidade())) {

			Visibilidade visivel = null;
			if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")) {
				visivel = Visibilidade.RESTRITA;
			} else {
				visivel = Visibilidade.PUBLICA;
			}

			criteria.add(Restrictions.like("visibilidade", visivel));
		}

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("dataCompromisso", filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("dataCompromisso", filtro.getDataAte()));
		}

		if (filtro.getDepartamentoPai() != null) {
			criteria.createAlias("departamento", "dep");
			criteria.add(Restrictions.eq("dep.id", filtro.getDepartamentoPai()
					.getId()));
		}

		if (filtro.getSubdepartamento() != null) {
			criteria.createAlias("subDepartamento", "subDep");
			criteria.add(Restrictions.eq("subDep.id", filtro
					.getSubdepartamento().getId()));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Compromisso> buscarPorDataAtual(Usuario userLogado) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Compromisso.class, "c");

		// Proje??o para serem retornados somente os campos necess?rios..
		ProjectionList projectionList = Projections.projectionList().create();
		projectionList
				.add(Projections.property("c.id").as("id"))
				.add(Projections.property("c.dataCompromisso").as(
						"dataCompromisso"))
				.add(Projections.property("c.descricao").as("descricao"))
				.add(Projections.property("c.horario").as("horario"));

		criteria.setProjection(projectionList);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setResultTransformer(
						new AliasToBeanResultTransformer(Compromisso.class));

		Date dataFinal = new Date(new Date().getTime()
				+ ((1000 * 24 * 60 * 60) * 3));

		criteria.add(Restrictions.ge("c.dataCompromisso", new Date()));
		criteria.add(Restrictions.le("c.dataCompromisso", dataFinal));
		criteria.add(Restrictions.eq("visibilidade", Visibilidade.RESTRITA));

		for (Grupo grupo : userLogado.getGrupos()) {
			if (grupo.getNome().equalsIgnoreCase("UNIDADE DE ARMAZENAMENTO")
					|| grupo.getNome().equalsIgnoreCase("ALMOXARIFADO")
					|| grupo.getNome().equalsIgnoreCase("ASSOCIACAO")
					|| grupo.getNome().equalsIgnoreCase("SPACO MOTOS")
					|| grupo.getNome().equalsIgnoreCase("AGRICOLA")) {
				criteria.add(Restrictions.eq("usuario", userLogado));
			}
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Compromisso> buscarPublicos() {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Compromisso.class, "c");

		// Proje??o para serem retornados somente os campos necess?rios..
		ProjectionList projectionList = Projections.projectionList().create();
		projectionList
				.add(Projections.property("c.id").as("id"))
				.add(Projections.property("c.dataCompromisso").as(
						"dataCompromisso"))
				.add(Projections.property("c.descricao").as("descricao"))
				.add(Projections.property("c.horario").as("horario"));

		criteria.setProjection(projectionList);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setResultTransformer(
						new AliasToBeanResultTransformer(Compromisso.class));

		Date dataFinal = new Date(new Date().getTime()
				+ ((1000 * 24 * 60 * 60) * 3));

		criteria.add(Restrictions.ge("c.dataCompromisso", new Date()));
		criteria.add(Restrictions.le("c.dataCompromisso", dataFinal));
		criteria.add(Restrictions.eq("visibilidade", Visibilidade.PUBLICA));

		return criteria.list();
	}

}
