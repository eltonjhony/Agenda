package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Grupo;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Grupos;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.repository.filter.UsuarioFilter;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private Grupos grupos;
	
	private UsuarioFilter filtro;
	private Usuario usuarioSelecionado;
	
	private List<Usuario> usuariosListados;
	private List<Grupo> gruposListados;
	
	public PesquisaUsuarioBean() {
		this.limpar();
	}
	
	
	private void limpar() {
		this.filtro = new UsuarioFilter();
		this.usuariosListados = new ArrayList<Usuario>();
		this.gruposListados = new ArrayList<Grupo>();
		
	}


	public void inicializar() {
		
		if(FacesUtil.isNotPostBack()) {
			this.gruposListados = grupos.todos();
		}
	}
	
	public void pesquisar() {
		this.usuariosListados = usuarios.filtrados(filtro);
	}
	
	public void excluir() {
		this.usuarioSelecionado = usuarios.excluir(this.usuarioSelecionado);
		this.usuariosListados = usuarios.todos();
		FacesUtil.addInfoMessage("Usuário excluído com sucesso!");
	}
	
	
	
	public UsuarioFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(UsuarioFilter filtro) {
		this.filtro = filtro;
	}
	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	
	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
	public List<Grupo> getGruposListados() {
		return gruposListados;
	}
	
	public List<Usuario> getUsuariosListados() {
		return usuariosListados;
	}
	
	public void setUsuariosListados(List<Usuario> usuariosListados) {
		this.usuariosListados = usuariosListados;
	}
	

}
