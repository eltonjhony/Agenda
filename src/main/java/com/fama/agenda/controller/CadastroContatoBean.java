package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.fama.agenda.model.Contato;
import com.fama.agenda.model.FormaContato;
import com.fama.agenda.model.TipoPessoa;
import com.fama.agenda.service.CadastroContatoService;
import com.fama.agenda.util.jsf.FacesUtil;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class CadastroContatoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroContatoService cadastroContatoService;

	private Contato contato;
	private List<FormaContato> listaFormaContatos;
	private FormaContato formaContato;
	private FormaContato formaContatoSelecionado;

	public void inicilizar() {

		if (FacesUtil.isNotPostBack()) {

			if (isEditando() && this.contato.getFormaContatos().size() > 0) {
				this.listaFormaContatos = this.contato.getFormaContatos();
			}

		}

	}

	public CadastroContatoBean() {
		this.limpar();
	}

	private void limpar() {
		this.contato = new Contato();
		this.formaContato = new FormaContato();
		this.listaFormaContatos = new ArrayList<FormaContato>();
		this.formaContatoSelecionado = new FormaContato();
	}

	public void salvar() {
		this.associarContato();
		this.contato = cadastroContatoService.salvar(contato);
		this.limpar();
		FacesUtil.addInfoMessage("Contato cadastrado com sucesso!");
	}

	private void associarContato() {

		if (this.listaFormaContatos.size() > 0) {

			for (FormaContato c : this.listaFormaContatos) {
				c.setContato(contato);
			}

		}

	}

	public void adicionarFormaContato() {

		// Reposiciona a lista que sera passada a Datatable se
		// o usuário estiver editando um contato que já tenha
		// formas de contatos cadastradas...
		if (isEditando() && !this.listaFormaContatos.isEmpty()) {
			List<FormaContato> parcial = new ArrayList<FormaContato>();
			parcial.addAll(listaFormaContatos);
			this.listaFormaContatos = new ArrayList<FormaContato>();

			for (FormaContato f : parcial) {
				this.listaFormaContatos.add(f);
			}

			this.contato.getFormaContatos().add(formaContato);
			
			if (!isEdicaoFormaContato()) {
				this.listaFormaContatos.add(formaContato);
			}

			// Caso não esteja editando um contato, o incremento da lista
			// passa a ser adicionado normalmente...
		} else {
			System.out.println("Entrou no else");
			this.contato.getFormaContatos().add(formaContato);

			if (!isEdicaoFormaContato()) {
				System.out.println("Entrou no if");
				this.listaFormaContatos.add(formaContato);
			}

		}

		this.formaContato = new FormaContato();
		this.formaContatoSelecionado = new FormaContato();

	}

	// Este método retorna verdadeiro se o usuário estiver
	// editando uma Forma de Contato para depois
	// chamar o método adicionarFormaContato()
	private boolean isEdicaoFormaContato() {
		return StringUtils.isNotBlank(this.formaContatoSelecionado.getNome())
				|| StringUtils.isNotBlank(this.formaContatoSelecionado
						.getEmail())
				|| StringUtils.isNotBlank(this.formaContatoSelecionado
						.getTelefone())
				|| StringUtils.isNotBlank(this.formaContatoSelecionado
						.getCelular());
	}

	public void removerFormaContato() {
		this.contato.getFormaContatos().remove(formaContatoSelecionado);
		this.listaFormaContatos.remove(formaContatoSelecionado);
		FacesUtil.addInfoMessage("Pressione Salvar para confirmar a alteração!");
		this.formaContatoSelecionado = new FormaContato();
	}

	public void limparListagem() {
		if (!isEditando()) {
			this.listaFormaContatos = new ArrayList<FormaContato>();
		}
	}

	public boolean isEditando() {
		return this.contato.getId() != null;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public List<FormaContato> getListaFormaContatos() {
		return listaFormaContatos;
	}

	public void setListaFormaContatos(List<FormaContato> listaFormaContatos) {
		this.listaFormaContatos = listaFormaContatos;
	}

	public FormaContato getFormaContato() {
		return formaContato;
	}

	public void setFormaContato(FormaContato formaContato) {
		this.formaContato = formaContato;
	}

	public FormaContato getFormaContatoSelecionado() {
		return formaContatoSelecionado;
	}

	public void setFormaContatoSelecionado(FormaContato formaContatoSelecionado) {
		this.formaContatoSelecionado = formaContatoSelecionado;
	}

	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}

}
