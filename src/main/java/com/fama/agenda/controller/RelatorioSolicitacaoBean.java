package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Solicitacoes;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.repository.filter.SolicitacaoFilter;
import com.fama.agenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class RelatorioSolicitacaoBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Solicitacoes solicitacoes;
	
	@Inject
	private Usuarios usuarios;
	
	private SolicitacaoFilter filtro;
	
	private List<Solicitacao> todosSolicitacoes;
	private List<Usuario> todosUsuarios;
	
	public RelatorioSolicitacaoBean() {
		this.todosSolicitacoes = new ArrayList<Solicitacao>();
		this.todosUsuarios = new ArrayList<Usuario>();
		this.filtro = new SolicitacaoFilter();
	}
	
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			todosUsuarios = usuarios.todos();
		}
	}
		
	
	public void emitir() {
		
		filtrar();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoimg1", "/relatorios/");

		
		if(this.todosSolicitacoes.size() > 0) {
			
		
			GeraRelatorio executor = new GeraRelatorio("/relatorios/relatorio_solicitacao.jasper",
					"relatorio_solicitacao",parametros,this.todosSolicitacoes);
			executor.chamarRelatorio();
			
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
	}
	


	private void filtrar() {
		
		this.todosSolicitacoes = solicitacoes.filtrados(filtro);	
		
	}

	public List<Solicitacao> getTodosSolicitacoes() {
		return todosSolicitacoes;
	}
	
	public void setTodosSolicitacoes(List<Solicitacao> todosSolicitacoes) {
		this.todosSolicitacoes = todosSolicitacoes;
	}
	
	public SolicitacaoFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(SolicitacaoFilter filtro) {
		this.filtro = filtro;
	}
	
	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}
	

}
