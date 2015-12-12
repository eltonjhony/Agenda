package com.fama.agenda.service;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.fama.agenda.model.Atividade;
import com.fama.agenda.model.Faixa;
import com.fama.agenda.repository.Atividades;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class CadastroAtividadeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Atividades atividades;

	private static final SimpleDateFormat SDF_CONVERT = new SimpleDateFormat(
			"HH:mm");
	private static Date DATA_FINAL_DIURNO = null;
	private static Date DATA_FINAL_VESPERTINO = null;
	
	
	@Transactional
	public Atividade salvar(Atividade atividade, List<Faixa> lista) {

		this.inicializarHorarios();
		Calendar data = Calendar.getInstance();
		data.add(Calendar.HOUR_OF_DAY, -2);
		atividade.setDataCriacao(data.getTime());
		atividade.setUsuario(new Seguranca().getUsuarioAtivo());
		if (!lista.isEmpty()) {
			atividade.getFaixas().clear();
			atividade.getFaixas().addAll(lista);
		}

		if (atividade.getHorarioDiurno() != null) {
			if (atividade.getHorarioDiurno().after(DATA_FINAL_DIURNO)) {
				throw new NegocioException(
						"O horário Diurno não pode ser maior que 11h:59");
			}
		}

		if (atividade.getHorarioVespertino() != null) {
			if (atividade.getHorarioVespertino().after(DATA_FINAL_VESPERTINO)
					|| atividade.getHorarioVespertino().before(
							DATA_FINAL_DIURNO)) {
				throw new NegocioException(
						"O intervalo de horário Vespertino é aceito entre 12h:00 até 18h:00");
			}
		}

		if (atividade.getHorarioNoturno() != null) {
			if (atividade.getHorarioNoturno().before(DATA_FINAL_VESPERTINO)) {
				throw new NegocioException(
						"O intervalo de horário Noturno é aceito entre 18h:01 até 23h:59");
			}
		}

		return this.atividades.guardar(atividade);
	}

	private void inicializarHorarios() {
		try {
			DATA_FINAL_DIURNO = SDF_CONVERT.parse("11:59");
			DATA_FINAL_VESPERTINO = SDF_CONVERT.parse("18:00");
		} catch (ParseException ex) {
			throw new NegocioException(
					"Ocorreu um erro de Conversão de Hora. \n"
							+ " Favor entrar em contato com o TI.");
		}

	}

}
