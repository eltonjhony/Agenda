package com.fama.agenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.fama.agenda.model.Oficio;
import com.fama.agenda.report.GeraRelatorio;
import com.fama.agenda.repository.Oficios;
import com.fama.agenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EmissaoOficioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	HttpServletRequest request;

	@Inject
	private Oficios oficios;

	private Oficio oficio;
	private String padrao;

	private List<Oficio> listaOficios = new ArrayList<Oficio>();

	public void inicializar() {
		String pagina = request.getServletPath();
		if (pagina.equals("/documentos/ImpressaoOficios.xhtml")) {
			listaOficios = oficios.todos();
		}
	}

	public EmissaoOficioBean() {
		String msg = "A Associação Cultural Fazendas Fama, vem solicitar a apresentação da Orquestra Jovem da Escola\n"
				+ " Municipal de Música Américo de Carvalho, na Biblioteca Fama no dia 23 de outubro de 2014 às 8h30\n"
				+ " para participação na III Semana Literária.";

		oficio = new Oficio();
		padrao = msg;
	}

	public void salvar() {
		if (!isEditando()) {
			inicializarNumeroOficio();
			atribuirIdManualmente();
		}
		this.oficio.setDataEmissao(new Date());
		this.oficio = this.oficios.guardar(oficio);
		this.oficio = new Oficio();
		FacesUtil.addInfoMessage("Ofício enviado com sucesso!\n"
				+ "Pressione 'Buscar Catálogo' para impressão. ");
	}

	// Método necessário para deixar a renderização do catálogo com o Id inicial novamente...
	// Este método adiciona manualmente um Id ao ofício.. 
	private void atribuirIdManualmente() {
		if (this.oficios.todos().isEmpty()) {
			this.oficio.setId(new Long(1));
		} else {
			List<Oficio> temp = this.oficios.buscarNumeroUltimoRegistro();
			this.oficio.setId(temp.get(0).getId() + 1);
		}

	}

	private void inicializarNumeroOficio() {
		List<Oficio> temp = this.oficios.buscarNumeroUltimoRegistro();
		Long numero = new Long(0);
		int ano;
		if (temp.size() != 0) {
			numero = temp.get(0).getId() + 1;
		} else {
			numero = new Long(1);
		}
		ano = Calendar.getInstance().get(Calendar.YEAR);
		oficio.setNumero(numero + "/" + ano);
	}

	public void emitir() {

		List<Oficio> documento = new ArrayList<Oficio>();
		documento.add(oficio);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("localizacaoImg1", "/relatorios/");
		parametros.put("dataAtual", new Date());

		GeraRelatorio executor = new GeraRelatorio(
				"/relatorios/documento_oficio.jasper", "documento_oficio.pdf",
				parametros, documento);
		executor.chamarRelatorio();

	}

	public void renderizar() {

		List<Long> ids = new ArrayList<Long>();

		for (Oficio oficio : this.oficios.todos()) {
			ids.add(oficio.getId());
		}

		this.oficios.renderizar(ids);
		FacesUtil.addInfoMessage("Catálogo renderizado com sucesso!");
		listaOficios = this.oficios.todos();
	}

	public Oficio getOficio() {
		return oficio;
	}

	public void setOficio(Oficio oficio) {
		this.oficio = oficio;
	}

	public List<Oficio> getListaOficios() {
		return listaOficios;
	}

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}

	public boolean isEditando() {
		return this.oficio.getId() != null;
	}

}
