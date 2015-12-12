package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.Departamento;
import com.fama.agenda.model.Visibilidade;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Compromissos;
import com.fama.agenda.repository.Departamentos;
import com.fama.agenda.repository.filter.CompromissoFilter;
import com.fama.agenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class RelatorioCompromissoBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Compromissos compromissos;
	
	@Inject
	private Departamentos repositoryDepartamentos;
	
	private CompromissoFilter filtro;
	
	private List<Compromisso> todosCompromissos;
	private List<Departamento> departamentos;
	private List<Departamento> subDepartamentos;
	
	public RelatorioCompromissoBean() {
		this.todosCompromissos = new ArrayList<Compromisso>();
		this.departamentos = new ArrayList<Departamento>();
		this.filtro = new CompromissoFilter();
	}
	
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			this.departamentos = repositoryDepartamentos.raizes();
			this.subDepartamentos = new ArrayList<Departamento>();
		}
	}
	
	public void carregarSubDepartamentos() {
		this.subDepartamentos = repositoryDepartamentos.subdepartamentosDe(
				filtro.getDepartamentoPai());
	}
		
	
	public void emitir() {
		
		filtrar();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoSubrelatorioCompromissos", "/relatorios/");
		parametros.put("localizacaoSubrelatorioCompromissos2", "/relatorios/");
		parametros.put("localizacaoimg1", "/relatorios/");
		
		if(this.todosCompromissos.size() > 0) {
			
			GeraRelatorio executor = new GeraRelatorio("/relatorios/relatorio_de_compromissos.jasper",
					"relatorio_compromissos",parametros,this.todosCompromissos);
			executor.chamarRelatorio();
			
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
	}
	


	private void filtrar() {
		
		this.todosCompromissos = compromissos.filtradosRelatorio(filtro);
		
	}

	
	public List<Compromisso> getTodosCompromissos() {
		return todosCompromissos;
	}
	
	public void setTodosCompromissos(List<Compromisso> todosCompromissos) {
		this.todosCompromissos = todosCompromissos;
	}
	
	public CompromissoFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(CompromissoFilter filtro) {
		this.filtro = filtro;
	}
	
	public List<Departamento> getDepartamentos() {
		return departamentos;
	}
	
	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
	public List<Departamento> getSubDepartamentos() {
		return subDepartamentos;
	}
	
	public void setSubDepartamentos(List<Departamento> subDepartamentos) {
		this.subDepartamentos = subDepartamentos;
	}
	
	public Visibilidade[] getVisibilidades() {
		return Visibilidade.values();
	}

}
