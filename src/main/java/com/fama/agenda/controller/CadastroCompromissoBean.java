package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.Contato;
import com.fama.agenda.model.Departamento;
import com.fama.agenda.model.FormaContato;
import com.fama.agenda.model.Visibilidade;
import com.fama.agenda.repository.Contatos;
import com.fama.agenda.repository.Departamentos;
import com.fama.agenda.service.CadastroCompromissoService;
import com.fama.agenda.service.NegocioException;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class CadastroCompromissoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Departamentos departamentos;

	@Inject
	private CadastroCompromissoService cadastroCompromissoService;

	@Inject
	private Contatos contatos;

	private List<Departamento> subdepartamentos;
	private List<Departamento> departamentosRaizes;

	private Compromisso compromisso;
	private FormaContato formaContato;
	private FormaContato formaContatoSelecionado;
	private Contato novoContato;
	private List<FormaContato> contatosAdicionados;
	private List<Contato> contatosCadastrados;
	private List<FormaContato> formasContatos;
	
	private String visualizacao = "";

	public void inicializar() {

		if (FacesUtil.isNotPostBack()) {

			departamentosRaizes = departamentos.raizes();

			if (compromisso.getDepartamento() != null) {
				carregarSubDepartamentos();
			}

			this.contatosCadastrados = contatos.todos();

			if (isEditando() && !this.compromisso.getFormaContato().isEmpty()) {
				this.formasContatos.addAll(compromisso.getFormaContato());
			}

			if (isEditando() && !this.compromisso.getContato().isEmpty()) {
				for (Contato c : this.compromisso.getContato()) {
					for (FormaContato f : contatos.porContato(c.getId())) {
						this.contatosAdicionados.add(f);
					}
				}
			}

		}

	}

	public CadastroCompromissoBean() {
		this.limpar();
	}

	public void carregarSubDepartamentos() {
		subdepartamentos = departamentos.subdepartamentosDe(compromisso
				.getDepartamento());
	}

	private void limpar() {
		this.compromisso = new Compromisso();
		this.novoContato = new Contato();
		this.formaContato = new FormaContato();
		this.formaContatoSelecionado = new FormaContato();
		this.subdepartamentos = new ArrayList<Departamento>();
		this.contatosAdicionados = new ArrayList<FormaContato>();
		this.contatosCadastrados = new ArrayList<Contato>();
		this.formasContatos = new ArrayList<FormaContato>();
	}

	public void salvar() {
		this.salvarFormasContato();
		this.compromisso = cadastroCompromissoService.salvar(this.compromisso);
		this.limpar();
		FacesUtil.addInfoMessage("Compromisso agendado com sucesso!");
	}

	private void salvarFormasContato() {
		if (this.formasContatos.size() > 0) {
			for (FormaContato f : this.formasContatos) {
				f.setCompromisso(compromisso); // Adiciona o mesmo compromisso
												// para uma Lista de Formas de
												// Contatos.
			}
			compromisso.setFormaContato(formasContatos); // Seta a lista de
															// formasContato
															// atualizada para o
															// compromisso.
		}
	}

	public void adicionarContato() {
		if (this.novoContato != null) {
			if (!this.novoContato.getFormaContatos().isEmpty()) {
				this.compromisso.getContato().add(novoContato);
				for (FormaContato f : contatos.porContato(novoContato.getId())) {
					this.contatosAdicionados.add(f);
				}
			} else {
				throw new NegocioException(
						"Este Contato não possui formas de contactá-lo. Ex.: (e-mail, telefone, celular).");
			}

		}

		this.novoContato = new Contato();

	}

	public void adicionarFormaContato() {
		if (this.formaContato != null) {
			this.formasContatos.add(formaContato);
		}

		this.formaContato = new FormaContato();

	}

	public void removerFormaContato() {
		this.compromisso.getFormaContato().remove(formaContato);
		this.formasContatos.remove(formaContato);
		this.formaContato = new FormaContato();
		FacesUtil.addInfoMessage("Pressione Salvar para concluir a alteração!");
	}

	public void removerContato() {
		this.compromisso.getContato().remove(
				formaContatoSelecionado.getContato());
		for (FormaContato temp : contatos.porContato(formaContatoSelecionado.getContato().getId())) {
			this.contatosAdicionados.remove(temp);
		}
		
		this.formaContato = new FormaContato();
		FacesUtil.addInfoMessage("Pressione Salvar para concluir a alteração!");
	}
	

	public Compromisso getCompromisso() {
		return compromisso;
	}

	public void setCompromisso(Compromisso compromisso) {
		this.compromisso = compromisso;
	}

	public FormaContato getFormaContato() {
		return formaContato;
	}

	public void setFormaContato(FormaContato formaContato) {
		this.formaContato = formaContato;
	}

	public Contato getNovoContato() {
		return novoContato;
	}

	public void setNovoContato(Contato novoContato) {
		this.novoContato = novoContato;
	}

	public FormaContato getFormaContatoSelecionado() {
		return formaContatoSelecionado;
	}

	public void setFormaContatoSelecionado(FormaContato formaContatoSelecionado) {
		this.formaContatoSelecionado = formaContatoSelecionado;
	}

	public List<FormaContato> getContatosAdicionados() {
		return contatosAdicionados;
	}

	public void setContatosAdicionados(List<FormaContato> contatosAdicionados) {
		this.contatosAdicionados = contatosAdicionados;
	}

	public List<Contato> getContatosCadastrados() {
		return contatosCadastrados;
	}

	public List<FormaContato> getFormasContatos() {
		return formasContatos;
	}

	public void setFormasContatos(List<FormaContato> formasContatos) {
		this.formasContatos = formasContatos;
	}

	public List<Departamento> getSubdepartamentos() {
		return subdepartamentos;
	}

	public void setSubdepartamentos(List<Departamento> subdepartamentos) {
		this.subdepartamentos = subdepartamentos;
	}

	public List<Departamento> getDepartamentosRaizes() {
		return departamentosRaizes;
	}

	public void setDepartamentosRaizes(List<Departamento> departamentosRaizes) {
		this.departamentosRaizes = departamentosRaizes;
	}
	
	public String getVisualizacao() {
		return visualizacao;
	}
	
	public void setVisualizacao(String visualizacao) {
		this.visualizacao = visualizacao;
	}

	public boolean isEditando() {
		return this.compromisso.getId() != null;
	}
	
	public Visibilidade[] getVisibilidades() {
		return Visibilidade.values();
	}
	
	

}
