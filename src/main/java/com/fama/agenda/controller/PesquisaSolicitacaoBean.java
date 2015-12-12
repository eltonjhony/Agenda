package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Solicitacoes;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.repository.filter.SolicitacaoFilter;
import com.fama.agenda.service.CadastroSolicitacaoService;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class PesquisaSolicitacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Solicitacoes solicitacoes;
	
	@Inject
	private CadastroSolicitacaoService cadastroSolicitacaoService;
	
	@Inject
	private Usuarios colaboradores;
	

	private SolicitacaoFilter filtro;
	
	private Solicitacao solicitacaoSelecionada;
	private List<Solicitacao> listaSolicitacoes = new ArrayList<Solicitacao>();
	private List<Usuario> listaColaboradores = new ArrayList<Usuario>();
	
	public PesquisaSolicitacaoBean() {
		this.filtro = new SolicitacaoFilter();
		this.solicitacaoSelecionada = new Solicitacao();
	}
	
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			this.listaColaboradores = colaboradores.todos();
		}
	}
	
	public void pesquisar() {
		this.listaSolicitacoes = cadastroSolicitacaoService.filtrar(filtro);
	}
	
	public void excluir() {
		solicitacoes.excluir(this.solicitacaoSelecionada);
		this.listaSolicitacoes = solicitacoes.filtrados(filtro);
		FacesUtil.addInfoMessage("Solicitacao excluída com sucesso!");
	}

	public SolicitacaoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(SolicitacaoFilter filtro) {
		this.filtro = filtro;
	}
	
	public List<Solicitacao> getListaSolicitacoes() {
		return listaSolicitacoes;
	}
	
	public void setListaSolicitacoes(List<Solicitacao> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	}
	
	public Solicitacao getSolicitacaoSelecionada() {
		return solicitacaoSelecionada;
	}
	
	public void setSolicitacaoSelecionada(Solicitacao solicitacaoSelecionada) {
		this.solicitacaoSelecionada = solicitacaoSelecionada;
	}
	
	public List<Usuario> getListaColaboradores() {
		return listaColaboradores;
	}

}
