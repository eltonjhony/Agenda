package com.fama.agenda.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.fama.agenda.model.Grupo;
import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Solicitacoes;
import com.fama.agenda.repository.filter.SolicitacaoFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.util.jpa.Transactional;

public class CadastroSolicitacaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Solicitacoes solicitacoes;

	public List<Solicitacao> filtrar(SolicitacaoFilter filtro) {

		restringirPesquisaPorColaborador(filtro);

		return this.solicitacoes.filtrados(filtro);
	}

	private void restringirPesquisaPorColaborador(SolicitacaoFilter filtro) {
		Usuario usuarioLogado = new Seguranca().getUsuarioAtivo();

		for (Grupo grupo : usuarioLogado.getGrupos()) {
			System.out.println("GRUPO: " + grupo.getNome());
			if (grupo.getNome().equalsIgnoreCase("UNIDADE DE ARMAZENAMENTO")
					|| grupo.getNome().equalsIgnoreCase("ALMOXARIFADO")
					|| grupo.getNome().equalsIgnoreCase("ASSOCIACAO")
					|| grupo.getNome().equalsIgnoreCase("SPACO MOTOS")
					|| grupo.getNome().equalsIgnoreCase("AGRICOLA")) {
				
				filtro.setColaborador(usuarioLogado);
			
			}
		}

	}

	@Transactional
	public void salvar(Solicitacao solicitacao) {
		
		if (solicitacao.getId() == null) {
			solicitacao.setDataPrimeiraCriacao(new Date());
		}
		
		solicitacao.setDataCriacao(new Date());
		this.solicitacoes.guardar(solicitacao);
		
	}
}
