package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Log;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Logs;
import com.fama.agenda.repository.filter.LogFilter;
import com.fama.agenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class RelatorioLogBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logs logs;
	
	private LogFilter filtro;
	
	private List<Log> todosLogs;
	
	public RelatorioLogBean() {
		this.todosLogs = new ArrayList<Log>();
		this.filtro = new LogFilter();
	}
		
	
	public void emitir() {
		
		filtrar();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoimg1", "/relatorios/");

		
		if(this.todosLogs.size() > 0) {
			
			GeraRelatorio executor = new GeraRelatorio("/relatorios/relatorio_log.jasper",
					"relatorio_log",parametros,this.todosLogs);
			executor.chamarRelatorio();
			
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
	}
	


	private void filtrar() {
		
		this.todosLogs = logs.filtrados(filtro);	
		
	}

	public List<Log> getTodosLogs() {
		return todosLogs;
	}
	
	public void setTodosLogs(List<Log> todosLogs) {
		this.todosLogs = todosLogs;
	}
	
	public LogFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(LogFilter filtro) {
		this.filtro = filtro;
	}

}
