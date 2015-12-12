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
import com.fama.agenda.service.CadastroUsuarioService;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	@Inject
	private Grupos grupos;

	private Usuario usuario;
	private Grupo novoGrupo;
	private Grupo grupoSelecionado;
	private List<Grupo> gruposAdicionados;
	private List<Grupo> gruposCadastrados;

	public CadastroUsuarioBean() {
		this.limpar();
	}

	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {
			gruposCadastrados = grupos.todos();

			if (!usuario.getGrupos().isEmpty()) {
				this.gruposAdicionados.addAll(usuario.getGrupos());
			}

		}

	}

	public void salvar() {
		cadastroUsuarioService.salvar(this.usuario);
		this.limpar();
		FacesUtil.addInfoMessage("Usuário cadastrado com sucesso!");

	}

	public void adicionarGrupo() {

		if (cadastroUsuarioService.validarGrupo(novoGrupo, gruposAdicionados)) {
			this.usuario.getGrupos().add(novoGrupo);
			this.gruposAdicionados.add(novoGrupo);
			this.novoGrupo = null;
		}

	}

	public void removerGrupo() {
		this.usuario.getGrupos().remove(grupoSelecionado);
		this.gruposAdicionados.remove(grupoSelecionado);

		FacesUtil
				.addInfoMessage("Clique em Salvar para confirmar a remoção do Grupo!");
		this.novoGrupo = null;
	}

	private void limpar() {
		usuario = new Usuario();
		gruposAdicionados = new ArrayList<Grupo>();
		this.novoGrupo = new Grupo();
		this.grupoSelecionado = new Grupo();
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupo getNovoGrupo() {
		return novoGrupo;
	}

	public void setNovoGrupo(Grupo novoGrupo) {
		this.novoGrupo = novoGrupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getGruposAdicionados() {
		return gruposAdicionados;
	}

	public void setGruposAdicionados(List<Grupo> gruposAdicionados) {
		this.gruposAdicionados = gruposAdicionados;
	}

	public List<Grupo> getGruposCadastrados() {
		return gruposCadastrados;
	}

}
