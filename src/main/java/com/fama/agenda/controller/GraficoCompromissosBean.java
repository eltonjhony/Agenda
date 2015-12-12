package com.fama.agenda.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.fama.agenda.repository.Graficos;

/**
 * @author Elton Jhony R. Oliveira
 */

@Named
@ViewScoped
public class GraficoCompromissosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static DateFormat DATA_FORMAT = new SimpleDateFormat("dd/MM");

	@Inject
	private Graficos graficos;

	private CartesianChartModel model;

	public void preRender() {
		this.model = new CartesianChartModel();
		adicionarSeries("Ocorrências de Compromissos");
	}

	private void adicionarSeries(String rotulo) {

		Map<Date, Long> valoresPorData = graficos.quantidadeCompromissoPorData(30);

		ChartSeries series = new ChartSeries(rotulo);

		for (Date data : valoresPorData.keySet()) {
			series.set(DATA_FORMAT.format(data), valoresPorData.get(data));
		}

		this.model.addSeries(series);
	}

	public CartesianChartModel getModel() {
		return model;
	}

}
