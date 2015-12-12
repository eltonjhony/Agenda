package com.fama.agenda.repository;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import com.fama.agenda.model.Atividade;
import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.vo.QuantidadeData;
import com.fama.agenda.model.vo.QuantidadeDataCompromisso;

public class Graficos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Map<Date, Long> quantidadeAtividadePorData(int numeroDeDias) {

		Session session = manager.unwrap(Session.class);

		numeroDeDias -= 1;

		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias);

		Map<Date, Long> resultado = criarMapaVazio(numeroDeDias, dataInicial);

		Criteria criteria = session.createCriteria(Atividade.class);
		criteria.add(Restrictions.le("dataAtividade", dataInicial.getTime()));
		criteria.add(Restrictions.ge("dataAtividade", new Date()));

		// select date(data_criacao) as data, count(id) as id
		// from atividades where data_criacao >= :dataInicial
		// group by date(data_criacao)
		
		criteria.setProjection(
				Projections
						.projectionList()
						.add(Projections.sqlGroupProjection(
								"date(data_atividade) as data", "date(data_atividade)",
								new String[] { "data" },
								new Type[] { StandardBasicTypes.DATE }))
						.add(Projections.count("id").as("quantidade")));

		@SuppressWarnings("unchecked")
		List<QuantidadeData> valoresPorData = criteria.setResultTransformer(
				Transformers.aliasToBean(QuantidadeData.class)).list();

		for (QuantidadeData quantData : valoresPorData) {
			resultado.put(quantData.getData(), quantData.getQuantidade());
		}

		return resultado;

	}
	
	public Map<Date, Long> quantidadeCompromissoPorData(int numeroDeDias) {

		Session session = manager.unwrap(Session.class);

		numeroDeDias -= 1;

		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias);

		Map<Date, Long> resultado = criarMapaVazio(numeroDeDias, dataInicial);

		Criteria criteria = session.createCriteria(Compromisso.class);

		criteria.add(Restrictions.le("dataCompromisso", dataInicial.getTime()));
		criteria.add(Restrictions.ge("dataCompromisso", new Date()));

		// select date(data_criacao) as data, count(id) as id
		// from compromissos where data_criacao >= :dataInicial
		// group by date(data_criacao)

		criteria.setProjection(
				Projections
						.projectionList()
						.add(Projections.sqlGroupProjection(
								"date(dt_compromisso) as data", "date(dt_compromisso)",
								new String[] { "data" },
								new Type[] { StandardBasicTypes.DATE }))
						.add(Projections.count("id").as("quantidade")));

		@SuppressWarnings("unchecked")
		List<QuantidadeDataCompromisso> valoresPorData = criteria.setResultTransformer(
				Transformers.aliasToBean(QuantidadeDataCompromisso.class)).list();

		for (QuantidadeDataCompromisso quantData : valoresPorData) {
			resultado.put(quantData.getData(), quantData.getQuantidade());	
		}

		return resultado;

	}

	private Map<Date, Long> criarMapaVazio(int numeroDeDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, Long> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), new Long(0));
			dataInicial.add(Calendar.DAY_OF_MONTH, -1);
		}

		return mapaInicial;
	}

}
