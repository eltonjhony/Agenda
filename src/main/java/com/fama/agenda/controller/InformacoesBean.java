package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Solicitacoes;
import com.fama.agenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class InformacoesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Solicitacoes solicitacoes;

	private Date dataCorrente = Calendar.getInstance(TimeZone.getDefault()).getTime();
	private List<Solicitacao> ultimaSolicitacao;

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			ultimaSolicitacao = solicitacoes.ultima();
		}
	}


	public void emitir() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoimg1", "/relatorios/");

		if (this.ultimaSolicitacao.size() > 0) {

			GeraRelatorio executor = new GeraRelatorio(
					"/relatorios/relatorio_solicitacao.jasper",
					"relatorio_solicitacao", parametros, this.ultimaSolicitacao);
			executor.chamarRelatorio();

		} else {
			FacesUtil
					.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	public Date getDataCorrente() {
		return dataCorrente;
	}
	
	public void setDataCorrente(Date dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public List<Solicitacao> getUltimaSolicitacao() {
		return ultimaSolicitacao;
	}

	public void setUltimaSolicitacao(List<Solicitacao> ultimaSolicitacao) {
		this.ultimaSolicitacao = ultimaSolicitacao;
	}

}
