package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Contato;
import com.fama.agenda.model.TipoPessoa;
import com.fama.agenda.repository.Contatos;
import com.fama.agenda.repository.filter.ContatoFilter;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class PesquisaContatoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Contatos contatos;
	

	private ContatoFilter filtro;
	
	private Contato contatoSelecionado;
	private List<Contato> listaContatos = new ArrayList<Contato>();
	
	public PesquisaContatoBean() {
		this.filtro = new ContatoFilter();
		this.contatoSelecionado = new Contato();
	}
	
	public void pesquisar() {
		this.listaContatos = contatos.filtrados(filtro);
	}
	
	public void excluir() {
		this.contatoSelecionado = contatos.excluir(this.contatoSelecionado);
		this.listaContatos = contatos.todos();
		FacesUtil.addInfoMessage("Contato excluído com sucesso!");
	}

	public ContatoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ContatoFilter filtro) {
		this.filtro = filtro;
	}
	
	public List<Contato> getListaContatos() {
		return listaContatos;
	}
	
	public void setListaContatos(List<Contato> listaContatos) {
		this.listaContatos = listaContatos;
	}
	
	public Contato getContatoSelecionado() {
		return contatoSelecionado;
	}
	
	public void setContatoSelecionado(Contato contatoSelecionado) {
		this.contatoSelecionado = contatoSelecionado;
	}
	
	public TipoPessoa[] getTipoPessoas() {
		return TipoPessoa.values();
	}
	

}
