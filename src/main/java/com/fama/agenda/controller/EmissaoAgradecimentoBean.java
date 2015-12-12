package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.fama.agenda.model.UsoImagem;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Oficios;
import com.fama.agenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmissaoAgradecimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Oficios oficios;
	
	private UsoImagem agradecimento = new UsoImagem();
	private List<UsoImagem> listaAgradecimentos = new ArrayList<UsoImagem>();
	private String padrao;
	
	public EmissaoAgradecimentoBean() {
		String msg = "É com grande prazer que agradecemos pela sua imensa contribuição à ASSOCIAÇÃO CULTURAL FAZENDAS FAMA\n"
				+ " os títulos de livros publicados pela Editora FTD. Esses ítens bibliográficos que nos concederam contrubuirão\n"
				+ " de maneira efetiva para a contínua realização do nosso trabalho junto à comunidade dessa cidade. Pois, nosso\n"
				+ " objetivo é promover o acesso a diversos gêneros de obras literárias e desenvolver atividades pedagógicas que\n"
				+ " despertem o gosto pela leitura.";
		padrao = msg;
	}
	
	public void inicializar() {
		if(FacesUtil.isNotPostBack()) {
			this.listaAgradecimentos = this.oficios.todas();
		}
	}
	
	
	public void salvar() {
		this.agradecimento.setData(new Date());
		this.agradecimento.setOp(1);
		this.agradecimento.setNome(this.agradecimento.getNome()+",");
		this.oficios.guardarImagem(agradecimento);
		this.agradecimento = new UsoImagem();
		FacesUtil.addInfoMessage("Agradecimento enviado com sucesso!\n"
				+ "Pressione 'Buscar Catálogo' para impressão. ");
	}
	
	public void emitir() {

		List<UsoImagem> documento = new ArrayList<UsoImagem>();
		documento.add(agradecimento);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoImg1", "/relatorios/");

		GeraRelatorio executor = new GeraRelatorio(
				"/relatorios/documento_agradecimento.jasper", "documento_agradecimento_doação.pdf",
				parametros, documento);
		executor.chamarRelatorio();

	}
	
	
	public UsoImagem getAgradecimento() {
		return agradecimento;
	}
	
	public void setAgradecimento(UsoImagem agradecimento) {
		this.agradecimento = agradecimento;
	}
	
	public String getPadrao() {
		return padrao;
	}
	
	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}
	
	public List<UsoImagem> getListaAgradecimentos() {
		return listaAgradecimentos;
	}

}
