package com.fama.agenda.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class GeraRelatorio {

	@Inject
	FacesContext facesContext;

	@Inject
	HttpServletResponse response;

	private String caminhoRelatorio;
	private String nomeArquivoSaida;
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private List<?> lista;

	public GeraRelatorio(String caminhoRelatorio, String nomeArquivoSaida,
			Map<String, Object> parametros, List<?> lista) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.nomeArquivoSaida = nomeArquivoSaida;
		this.parametros = parametros;
		this.lista = lista;
	}

	public void chamarRelatorio() {

		facesContext = FacesContext.getCurrentInstance();

		response = (HttpServletResponse) facesContext.getExternalContext()
				.getResponse();

		response.setContentType("application/pdf");

		response.setHeader("Content-disposition", "inline;filename=" + "/"
				+ this.nomeArquivoSaida + ".pdf");

		try {

			// Carrega o relatorio pegando o caminho do arquivo .jasper.
			JasperReport report = (JasperReport) JRLoader.loadObject(getClass()
					.getClassLoader().getResource(this.caminhoRelatorio));

			ServletOutputStream servletOutputStream = response
					.getOutputStream();

			// Transforma um Objeto List em um DataSource para popular o report..
			JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(
					lista);

			JasperPrint impressoraJasper = JasperFillManager.fillReport(report,
					parametros, datasource);

			JasperExportManager.exportReportToPdfStream(impressoraJasper,
					servletOutputStream);

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			facesContext.responseComplete();
		}

	}

}
