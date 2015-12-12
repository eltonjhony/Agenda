package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Contato;
import com.fama.agenda.model.TipoPessoa;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Contatos;
import com.fama.agenda.repository.filter.ContatoFilter;
import com.fama.agenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class RelatorioContatoBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Contatos contatos;
	
	private ContatoFilter filtro;
	
	private List<Contato> todosContatos;
	
	public RelatorioContatoBean() {
		this.todosContatos = new ArrayList<Contato>();
		this.filtro = new ContatoFilter();
	}
		
	
	public void emitir() {
		
		filtrar();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoSubrelatorioContatos", "/relatorios/");
		parametros.put("localizacaoimg1", "/relatorios/");
		
		if(this.todosContatos.size() > 0) {
			
			GeraRelatorio executor = new GeraRelatorio("/relatorios/relatorio_contatos.jasper",
					"relatorio_contatos",parametros,this.todosContatos);
			executor.chamarRelatorio();
			
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
	}
	


	private void filtrar() {
		
		this.todosContatos = contatos.Relatoriofiltrados(filtro);
		
	}

	
	public List<Contato> getTodosContatos() {
		return todosContatos;
	}
	
	public void setTodosContatos(List<Contato> todosContatos) {
		this.todosContatos = todosContatos;
	}
	
	public ContatoFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(ContatoFilter filtro) {
		this.filtro = filtro;
	}
	
	public TipoPessoa[] getTipoPessoas() {
		return TipoPessoa.values();
	}

}
