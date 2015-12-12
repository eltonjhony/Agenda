package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;

import com.fama.agenda.model.Recado;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.service.CadastroRecadoService;
import com.fama.agenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroRecadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Inject
	CadastroRecadoService cadastroRecadoService;

	private Recado recado;

	private Usuario novoUsuario;
	private Usuario usuarioSelecionado;

	private List<Usuario> todosUsuarios;
	private List<Usuario> usuariosAdicionados;

	public CadastroRecadoBean() {
		this.limpar();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			this.todosUsuarios = usuarios.todos();
		}
	}

	private void limpar() {
		this.recado = new Recado();
		this.novoUsuario = new Usuario();
		this.usuarioSelecionado = new Usuario();
		this.todosUsuarios = new ArrayList<Usuario>();
		this.usuariosAdicionados = new ArrayList<Usuario>();
	}

	public void enviar() {

		if (usuariosAdicionados.size() < 1) {
			this.usuariosAdicionados = this.usuarios.todos();
		}

		if (this.cadastroRecadoService.enviarEmail(usuariosAdicionados, null,
				recado)) {
			this.cadastroRecadoService.salvar(recado);
			FacesUtil.addInfoMessage("E-mail enviado com sucesso!");
		}

	}

	public void adicionarUsuario() {
		if (novoUsuario != null) {
			this.usuariosAdicionados.add(novoUsuario);
		}

		this.novoUsuario = new Usuario();
	}

	public void removerUsuario() {

		if (usuarioSelecionado != null) {
			this.usuariosAdicionados.remove(usuarioSelecionado);
		}

		this.usuarioSelecionado = new Usuario();
	}

	public Recado getRecado() {
		return recado;
	}

	public void setRecado(Recado recado) {
		this.recado = recado;
	}

	public Usuario getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Usuario novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getUsuariosAdicionados() {
		return usuariosAdicionados;
	}

	public void setUsuariosAdicionados(List<Usuario> usuariosAdicionados) {
		this.usuariosAdicionados = usuariosAdicionados;
	}

	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}
}
