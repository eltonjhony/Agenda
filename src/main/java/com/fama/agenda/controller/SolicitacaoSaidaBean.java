package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.service.CadastroSolicitacaoService;
import com.fama.agenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SolicitacaoSaidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroSolicitacaoService cadastroSolicitacaoService;

	@Inject
	private Usuarios usuarios;

	private Solicitacao solicitacao;
	private String visualizacao = "";

	private List<Usuario> colaboradores;

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			this.colaboradores = usuarios.todos();
		}
	}

	public SolicitacaoSaidaBean() {
		this.solicitacao = new Solicitacao();
		this.colaboradores = new ArrayList<Usuario>();
	}

	public void salvar() {
		this.cadastroSolicitacaoService.salvar(solicitacao);
		if (isEditando()) {
			FacesUtil.addInfoMessage("Solicitação atualizada com sucesso!");
		} else {
			FacesUtil.addInfoMessage("Solicitação enviada com sucesso!");
		}
		this.solicitacao = new Solicitacao();
	}

	public List<Usuario> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Usuario> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public String getVisualizacao() {
		return visualizacao;
	}

	public void setVisualizacao(String visualizacao) {
		this.visualizacao = visualizacao;
	}

	public boolean isEditando() {
		return this.solicitacao.getId() != null;
	}
}
