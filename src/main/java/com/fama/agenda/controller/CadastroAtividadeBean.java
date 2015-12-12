package com.fama.agenda.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fama.agenda.model.Atividade;
import com.fama.agenda.model.Contato;
import com.fama.agenda.model.Faixa;
import com.fama.agenda.model.FormaContato;
import com.fama.agenda.model.TipoAtividade;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Contatos;
import com.fama.agenda.repository.FaixaEtarias;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.service.CadastroAtividadeService;
import com.fama.agenda.service.NegocioException;
import com.fama.agenda.util.jsf.FacesUtil;

 /**
  * @author Elton Jhony R. Oliveira
  */

@Named
@ViewScoped
public class CadastroAtividadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroAtividadeService cadastroAtividadeService;

	@Inject
	private FaixaEtarias faixaRepository;

	@Inject
	private Usuarios usuarios;

	@Inject
	private Contatos contatos;

	private Atividade atividade;
	private Usuario novoColaborador;

	private List<Faixa> faixas;
	private List<Usuario> listaUsuarios;
	private List<Usuario> colaboradores;

	private FormaContato formaContato;
	private Contato novoContato;
	private List<FormaContato> contatosAdicionados;
	private List<Contato> contatosCadastrados;
	private List<FormaContato> formasContatos;
	private List<Faixa> faixasEscolhidas;

	public void inicializar() {
		if (FacesUtil.isNotPostBack()) {
			this.faixas = faixaRepository.todos();
			this.listaUsuarios = usuarios.todos();
			this.contatosCadastrados = contatos.todos();

			if(isEditando() && !this.atividade.getFaixas().isEmpty()) {
				this.faixasEscolhidas.addAll(this.atividade.getFaixas());
			}
			
			if(isEditando() && !this.atividade.getColaboradores().isEmpty()) {
				this.colaboradores.addAll(this.atividade.getColaboradores());
			}
			
			if (isEditando() && !this.atividade.getFormaContato().isEmpty()) {
				this.formasContatos.addAll(atividade.getFormaContato());
			}

			if (isEditando() && !this.atividade.getContato().isEmpty()) {
				for (Contato c : this.atividade.getContato()) {
					for (FormaContato f : contatos.porContato(c.getId())) {
						this.contatosAdicionados.add(f);
					}
				}
			}

		}
	}

	

	public CadastroAtividadeBean() {
		this.limpar();
	}

	public void salvar() {
		this.salvarFormasContato();
		this.atividade = cadastroAtividadeService.salvar(atividade,
				this.faixasEscolhidas);
		this.limpar();
		FacesUtil.addInfoMessage("Atividade Agendada com sucesso!");
	}

	public void adicionarColaborador() {
		this.atividade.getColaboradores().add(novoColaborador);
		this.colaboradores.add(novoColaborador);
		novoColaborador = new Usuario();
	}

	private void limpar() {
		this.atividade = new Atividade();
		this.novoColaborador = new Usuario();
		this.novoContato = new Contato();
		this.formaContato = new FormaContato();
		this.colaboradores = new ArrayList<Usuario>();
		this.contatosAdicionados = new ArrayList<FormaContato>();
		this.contatosCadastrados = new ArrayList<Contato>();
		this.formasContatos = new ArrayList<FormaContato>();
		this.faixasEscolhidas = new ArrayList<Faixa>();
	}

	private void salvarFormasContato() {
		if (this.formasContatos.size() > 0) {
			for (FormaContato f : this.formasContatos) {
				f.setAtividade(atividade); // Adiciona a mesma atividade para
											// uma Lista de Formas de Contatos.
			}
			atividade.getFormaContato().addAll(formasContatos); // Seta a lista de
														// formasContato
														// atualizada para a
														// atividade.
		}
	}

	public void adicionarContato() {
		if (this.novoContato != null) {
			this.atividade.getContato().add(novoContato);
			if (!this.novoContato.getFormaContatos().isEmpty()) {
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
		this.atividade.getFormaContato().remove(formaContato);
		this.formasContatos.remove(formaContato);
		this.formaContato = new FormaContato();
		FacesUtil.addInfoMessage("Pressione Salvar para concluir a alteração!");
	}

	public void removerContato() {
		this.atividade.getContato().remove(
				formaContato.getContato());
		for (FormaContato temp : contatos.porContato(formaContato.getContato().getId())) {
			this.contatosAdicionados.remove(temp);
		}
		
		this.formaContato = new FormaContato();
		FacesUtil.addInfoMessage("Pressione Salvar para concluir a alteração!");
	}
	
	public void removerColaborador() {
		this.atividade.getColaboradores().remove(novoColaborador);
		this.colaboradores.remove(novoColaborador);
		this.novoColaborador = new Usuario();
		FacesUtil.addInfoMessage("Pressione Salvar para concluir a alteração!");
	}
	
	public boolean isEditando() {
		return this.atividade.getId() != null;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Usuario getNovoColaborador() {
		return novoColaborador;
	}

	public void setNovoColaborador(Usuario novoColaborador) {
		this.novoColaborador = novoColaborador;
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

	public List<Faixa> getFaixas() {
		return faixas;
	}

	public void setFaixas(List<Faixa> faixas) {
		this.faixas = faixas;
	}

	public List<Usuario> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Usuario> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public List<FormaContato> getContatosAdicionados() {
		return contatosAdicionados;
	}
	
	public List<Faixa> getFaixasEscolhidas() {
		return faixasEscolhidas;
	}
	
	public void setFaixasEscolhidas(List<Faixa> faixasEscolhidas) {
		this.faixasEscolhidas = faixasEscolhidas;
	}

	public void setContatosAdicionados(List<FormaContato> contatosAdicionados) {
		this.contatosAdicionados = contatosAdicionados;
	}

	public List<FormaContato> getFormasContatos() {
		return formasContatos;
	}

	public void setFormasContatos(List<FormaContato> formasContatos) {
		this.formasContatos = formasContatos;
	}

	public List<Contato> getContatosCadastrados() {
		return contatosCadastrados;
	}

	public TipoAtividade[] getAtividades() {
		return TipoAtividade.values();
	}

}
