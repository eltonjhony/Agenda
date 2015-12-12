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

import com.fama.agenda.model.Adesao;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Termos;
import com.fama.agenda.util.jsf.FacesUtil;


@Named
@ViewScoped
public class TermoAdesaoBean implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private Termos termos;
	
	
	private List<Adesao> lista;
	private Adesao adesao = new Adesao();
	
	public void inicializar() {
		this.lista = this.termos.todos();
	}
	
	public TermoAdesaoBean() {
		this.lista = new ArrayList<Adesao>();
	}
	
	public void salvar() {
		this.adesao.setData(new Date());
		this.adesao = this.termos.guardar(this.adesao);
		this.adesao = new Adesao();
		FacesUtil.addInfoMessage("Termo de Adesão enviado com sucesso!\n"
				+ "Pressione 'Buscar Catálogo' para impressão. ");
	}
		
	
	public void emitir() {
		

		List<Adesao> termo = new ArrayList<Adesao>();
		termo.add(adesao);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoImg1", "/relatorios/");

		
		if(this.lista.size() > 0) {
			
			GeraRelatorio executor = new GeraRelatorio("/relatorios/documento_trabalho_voluntario.jasper",
					"relatorio_log",parametros,termo);
			executor.chamarRelatorio();
			
		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
		
	}
	

	public List<Adesao> getLista() {
		return lista;
	}
	
	public void setLista(List<Adesao> lista) {
		this.lista = lista;
	}

	public Adesao getAdesao() {
		return adesao;
	}
	
	public void setAdesao(Adesao adesao) {
		this.adesao = adesao;
	}

}
