package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.Departamento;
import com.fama.agenda.model.Visibilidade;
import com.fama.agenda.repository.Compromissos;
import com.fama.agenda.repository.Departamentos;
import com.fama.agenda.repository.filter.CompromissoFilter;
import com.fama.agenda.service.CadastroCompromissoService;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class PesquisaCompromissoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Compromissos compromissos;
	
	@Inject
	private CadastroCompromissoService cadastroCompromissoService;

	@Inject
	private Departamentos departamentos;
	
	private CompromissoFilter filtro;
	
	private Compromisso compromisso;

	private List<Compromisso> listaCompromissos;
	private List<Departamento> departamentosPai;
	private List<Departamento> subDepartamentos;

	public PesquisaCompromissoBean() {
		this.limpar();
	}

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			this.departamentosPai = departamentos.raizes();
			
			if(filtro.getDepartamentoPai() != null) {
				carregarSubDepartamentos();
			}
			
		}
	}

	private void limpar() {
		this.filtro = new CompromissoFilter();
		this.compromisso = new Compromisso();
		this.listaCompromissos = new ArrayList<Compromisso>();
		this.departamentosPai = new ArrayList<Departamento>();
		this.subDepartamentos = new ArrayList<Departamento>();
	}
	
	public void pesquisar() {
		this.listaCompromissos = cadastroCompromissoService.filtrar(filtro);
	}
	
	public void excluir() {
		compromissos.excluir(this.compromisso);
		this.listaCompromissos = compromissos.filtrados(new CompromissoFilter());
		FacesUtil.addInfoMessage("Compromisso excluído com sucesso!");
	}
	
	public void carregarSubDepartamentos() {
		this.subDepartamentos = departamentos.subdepartamentosDe(
				filtro.getDepartamentoPai());
	}
	
	public CompromissoFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(CompromissoFilter filtro) {
		this.filtro = filtro;
	}
	
	public Compromisso getCompromisso() {
		return compromisso;
	}
	
	public void setCompromisso(Compromisso compromisso) {
		this.compromisso = compromisso;
	}

	public List<Compromisso> getListaCompromissos() {
		return listaCompromissos;
	}

	public void setListaCompromissos(List<Compromisso> listaCompromissos) {
		this.listaCompromissos = listaCompromissos;
	}

	public List<Departamento> getDepartamentosPai() {
		return departamentosPai;
	}

	public List<Departamento> getSubDepartamentos() {
		return subDepartamentos;
	}
	
	public Visibilidade[] getVisibilidades() {
		return Visibilidade.values();
	}

}
