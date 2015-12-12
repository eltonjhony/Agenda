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

import com.fama.agenda.model.Atividade;
import com.fama.agenda.model.Log;
import com.fama.agenda.model.TipoAtividade;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.filter.AtividadeFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.service.NegocioException;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class Atividades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Atividade porId(Long id) {
		return this.manager.find(Atividade.class, id);
	}

	public Atividade guardar(Atividade atividade) {
		return this.manager.merge(atividade);
	}

	@SuppressWarnings("unchecked")
	public List<Atividade> filtrados(AtividadeFilter filtro) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session
				.createCriteria(Atividade.class, "a")
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("a.id").as("id"))
								.add(Projections.property("a.dataAtividade")
										.as("dataAtividade"))
								.add(Projections.property("a.nomeAtividade")
										.as("nomeAtividade"))
								.add(Projections.property("a.tipoAtividade")
										.as("tipoAtividade")))
				.setResultTransformer(
						new AliasToBeanResultTransformer(Atividade.class));

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("dataAtividade", filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("dataAtividade", filtro.getDataAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nomeAtividade", filtro.getNome(),
					MatchMode.START));
		}

		if (filtro.getTipos() != null) {
			criteria.add(Restrictions.eq("tipoAtividade", filtro.getTipos()));
		}

		return criteria.list();
	}

	@Transactional
	public void excluir(Atividade atividadeSelecionada) {
		Atividade temp = null;
		try {
			temp = this.manager.find(Atividade.class,
					atividadeSelecionada.getId());
			this.manager.remove(temp);
			
			// Insere um Log de exclusão logo após o comando remove..
			// caso aconteça um PersistenceException ele não atualiza...
			Log log = new Log();
			Usuario usuario = new Seguranca().getUsuarioAtivo();
			log.setDataOperacao(new Date());
			log.setDescricao("Atividade excluida: "
					+ atividadeSelecionada.getNomeAtividade());
			log.setUsuario(usuario);
			this.manager.merge(log);
			
			this.manager.flush();
		} catch (PersistenceException ex) {
			throw new NegocioException(
					"A atividade que você deseja excluir possui vínculos com outras operações do sistema."
							+ " Não é permitida a remoção!");
		}

	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Atividade> filtradosRelatorio(AtividadeFilter filtro, int op) {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Atividade.class);
				
		if(op == 1) {
			criteria.setFetchMode("faixas", FetchMode.JOIN);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		} else {
			criteria.setFetchMode("colaboradores", FetchMode.JOIN);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("dataAtividade", filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("dataAtividade", filtro.getDataAte()));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nomeAtividade", filtro.getNome(),
					MatchMode.START));
		}

		if (op == 1) {
			criteria.add(Restrictions
					.eq("tipoAtividade", TipoAtividade.INTERNA));
		}

		if (op == 2) {
			criteria.add(Restrictions
					.eq("tipoAtividade", TipoAtividade.EXTERNA));
		}

		return criteria.list();
	}
}
