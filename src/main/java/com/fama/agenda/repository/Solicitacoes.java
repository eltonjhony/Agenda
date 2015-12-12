package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.fama.agenda.model.Log;
import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.filter.SolicitacaoFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.service.NegocioException;

public class Solicitacoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Solicitacao porId(Long id) {
		return this.manager.find(Solicitacao.class, id);
	}

	public Solicitacao guardar(Solicitacao solicitacao) {
		return this.manager.merge(solicitacao);
	}

	@SuppressWarnings("unchecked")
	public List<Solicitacao> filtrados(SolicitacaoFilter filtro) {

		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Solicitacao.class);

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataAte()));
		}

		if (filtro.getDataSolicitacao() != null) {
			System.out.println("entrou");
			criteria.add(Restrictions.eq("dataCriacao",
					filtro.getDataSolicitacao()));
		}

		if (filtro.getColaborador() != null) {
			criteria.add(Restrictions.eq("colaborador", filtro.getColaborador()));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Solicitacao> ultima() {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Solicitacao.class);

		criteria.addOrder(Order.desc("id"));
		return criteria.setMaxResults(1).list();

	}

	public void excluir(Solicitacao solicitacaoSelecionada) {
		try {
			Solicitacao temp = this.manager.find(Solicitacao.class,
					solicitacaoSelecionada.getId());
			this.manager.remove(temp);

			// Grava na tabela log_exclusão o registro que está para ser
			// excluído...
			Log log = new Log();
			Usuario usuario = new Seguranca().getUsuarioAtivo();
			log.setDataOperacao(new Date());
			log.setDescricao("Solicitação excluida: " + temp.getId());
			log.setUsuario(usuario);
			this.manager.merge(log);

			this.manager.flush();
		} catch (PersistenceException ex) {
			throw new NegocioException(
					"A solicitação que você deseja excluir possui vínculos com outras operações do sistema."
							+ " Não é permitida a remoção!");
		}

	}

}
