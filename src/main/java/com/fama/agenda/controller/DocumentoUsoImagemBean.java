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

@Named
@ViewScoped
public class DocumentoUsoImagemBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Oficios oficios;

	private UsoImagem usoImagem = new UsoImagem();
	private UsoImagem imagem = new UsoImagem();

	
	public void salvar() {
		usoImagem.setData(new Date());
		this.imagem = oficios.guardarImagem(usoImagem);
		this.usoImagem = new UsoImagem();
	}

	public void emitir() {
		
		UsoImagem doc = imagem;
		imagem = new UsoImagem();
		
		List<UsoImagem> documento = new ArrayList<UsoImagem>();
		documento.add(doc);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoImg1", "/relatorios/");

		GeraRelatorio executor = new GeraRelatorio(
				"/relatorios/documento_uso_imagem.jasper", "documento_uso_imagem",
				parametros, documento);
		executor.chamarRelatorio();
	}
	
	public UsoImagem getUsoImagem() {
		return usoImagem;
	}
	
	public void setUsoImagem(UsoImagem usoImagem) {
		this.usoImagem = usoImagem;
	}
	
	public boolean isEditando() {
		return this.imagem.getId() != null;
	}

}
