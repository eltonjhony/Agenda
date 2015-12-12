package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Atividade;
import com.fama.agenda.model.Faixa;
import com.fama.agenda.model.TipoAtividade;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Atividades;
import com.fama.agenda.repository.FaixaEtarias;
import com.fama.agenda.repository.filter.AtividadeFilter;
import com.fama.agenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class RelatorioAtividadeInternaBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Atividades atividades;
	
	@Inject
	private FaixaEtarias faixas;
	
	
	private AtividadeFilter filtro;
	
	private List<Atividade> todosAtividades;
	private List<Faixa> faixasEtarias;
	
	public RelatorioAtividadeInternaBean() {
		this.todosAtividades = new ArrayList<Atividade>();
		this.filtro = new AtividadeFilter();
		this.faixasEtarias = new ArrayList<Faixa>();
	}
	
		
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			this.faixasEtarias = faixas.todos();
		}
	}
	
	public void emitir() {
		
		filtrar();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoSubrelatorioFaixas", "/relatorios/");
		parametros.put("localizacaoimg1", "/relatorios/");
		

		
		if(this.todosAtividades.size() > 0) {
			
			GeraRelatorio executor = new GeraRelatorio("/relatorios/relatorio_atividades_internas.jasper",
					"relatorio_atividades_internas",parametros,this.todosAtividades);
			executor.chamarRelatorio();
			
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
	}
	


	private void filtrar() {
		this.todosAtividades = atividades.filtradosRelatorio(filtro,1);
	}


	
	public List<Atividade> getTodosAtividades() {
		return todosAtividades;
	}
	
	public void setTodosAtividades(List<Atividade> todosAtividades) {
		this.todosAtividades = todosAtividades;
	}
	
	public AtividadeFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(AtividadeFilter filtro) {
		this.filtro = filtro;
	}
	
	public List<Faixa> getFaixasEtarias() {
		return faixasEtarias;
	}
	
	public TipoAtividade[] getTipoAtividades() {
		return TipoAtividade.values();
	}

}
