package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Atividade;
import com.fama.agenda.model.Faixa;
import com.fama.agenda.model.TipoAtividade;
import com.fama.agenda.repository.Atividades;
import com.fama.agenda.repository.FaixaEtarias;
import com.fama.agenda.repository.filter.AtividadeFilter;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class PesquisaAtividadeBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Atividades atividades;
	
	@Inject
	private FaixaEtarias faixaEtarias;
	
	private AtividadeFilter filtro;
	private Atividade atividadeSelecionada;
	
	private List<Faixa> faixas;
	private List<Atividade> listaAtividades;
	
	public void inicializar() {
		
		if(FacesUtil.isNotPostBack()) {
			this.faixas = faixaEtarias.todos();
		}
		
	}
	
	public PesquisaAtividadeBean() {
		this.limpar();
	}
	
	private void limpar() {
		this.faixas = new ArrayList<Faixa>();
		this.filtro = new AtividadeFilter();
		this.atividadeSelecionada = new Atividade();
		this.listaAtividades = new ArrayList<Atividade>();
	}
	
	public void pesquisar() {
		this.listaAtividades = atividades.filtrados(filtro);
	}
	
	public void excluir() {
		this.atividades.excluir(this.atividadeSelecionada);
		this.listaAtividades = atividades.filtrados(new AtividadeFilter());
		FacesUtil.addInfoMessage("Atividade excluída com sucesso!");
	}
	
	public AtividadeFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(AtividadeFilter filtro) {
		this.filtro = filtro;
	}
	
	public Atividade getAtividadeSelecionada() {
		return atividadeSelecionada;
	}
	
	public void setAtividadeSelecionada(Atividade atividadeSelecionada) {
		this.atividadeSelecionada = atividadeSelecionada;
	}

	public List<Faixa> getFaixas() {
		return faixas;
	}
	
	public List<Atividade> getListaAtividades() {
		return listaAtividades;
	}
	
	public void setListaAtividades(List<Atividade> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}
	
	public TipoAtividade[] getTipoAtividades() {
		return TipoAtividade.values();
	}

}
