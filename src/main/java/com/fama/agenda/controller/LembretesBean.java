package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Compromissos;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.util.jsf.FacesUtil;

@Named
@SessionScoped
public class LembretesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Compromissos compromissos;

	private boolean exiba;
	private Integer cont = 1;
	private List<Compromisso> proximosCompromissos = new ArrayList<Compromisso>();
	private List<Compromisso> proximosCompromissosPublicos = new ArrayList<Compromisso>();

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			verificaProximosCompromissos();
		}
	}

	private void verificaProximosCompromissos() {

		Usuario userLogado = new Seguranca().getUsuarioAtivo();
		this.proximosCompromissos = compromissos.buscarPorDataAtual(userLogado);
		this.proximosCompromissosPublicos = compromissos.buscarPublicos();

		if (this.proximosCompromissos.size() > 0
				|| this.proximosCompromissosPublicos.size() > 0) {
			if (cont == 1) {
				cont++;
				exiba = true;
			} else {
				exiba = false;
			}
		} else {
			exiba = false;
		}

	}

	public boolean isExiba() {
		return exiba;
	}

	public void setExiba(boolean exiba) {
		this.exiba = exiba;
	}

	public List<Compromisso> getProximosCompromissos() {
		return proximosCompromissos;
	}

	public void setProximosCompromissos(List<Compromisso> proximosCompromissos) {
		this.proximosCompromissos = proximosCompromissos;
	}

	public List<Compromisso> getProximosCompromissosPublicos() {
		return proximosCompromissosPublicos;
	}

	public void setProximosCompromissosPublicos(
			List<Compromisso> proximosCompromissosPublicos) {
		this.proximosCompromissosPublicos = proximosCompromissosPublicos;
	}

}
