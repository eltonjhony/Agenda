package com.fama.agenda.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.model.Departamento;
import com.fama.agenda.model.Grupo;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Compromissos;
import com.fama.agenda.repository.filter.CompromissoFilter;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class CadastroCompromissoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Compromissos compromissos;

	@Transactional
	public Compromisso salvar(Compromisso compromisso) {

		Calendar data = Calendar.getInstance();
		data.add(Calendar.HOUR_OF_DAY, -2);
		compromisso.setDataCriacao(data.getTime());
		
		
		compromisso.setUsuario(new Seguranca().getUsuarioAtivo());

		return this.compromissos.guardar(compromisso);
	}

	public List<Compromisso> filtrar(CompromissoFilter filtro) {

		this.validarConsultaPorGrupoDeUsuario(filtro);

		return this.compromissos.filtrados(filtro);
	}

	private void validarConsultaPorGrupoDeUsuario(CompromissoFilter filtro) {

		Usuario usuarioLogado = new Seguranca().getUsuarioAtivo();

		for (Grupo grupo : usuarioLogado.getGrupos()) {

			if (grupo.getNome().equals("UNIDADE DE ARMAZENAMENTO")) {
				if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")
						|| !StringUtils.isNotBlank(filtro.getVisibilidade())) {
					Departamento dep = compromissos
							.porDepartamento("Unidade de Armazenamento");
					filtro.setDepartamentoPai(dep);
					filtro.setSubdepartamento(null);
				}
			} else if (grupo.getNome().equals("ALMOXARIFADO")) {
				if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")
						|| !StringUtils.isNotBlank(filtro.getVisibilidade())) {
					Departamento dep = compromissos
							.porDepartamento("Almoxarifado");
					filtro.setDepartamentoPai(dep);
					filtro.setSubdepartamento(null);
				}
			} else if (grupo.getNome().equals("AGRICOLA")) {
				if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")
						|| !StringUtils.isNotBlank(filtro.getVisibilidade())) {
					Departamento dep = compromissos
							.porDepartamento("Agrícola");
					filtro.setDepartamentoPai(dep);
					filtro.setSubdepartamento(null);
				}
			} else if (grupo.getNome().equals("ASSOCIACAO")) {
				if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")
						|| !StringUtils.isNotBlank(filtro.getVisibilidade())) {
					Departamento dep = compromissos
							.porDepartamento("Associação");
					filtro.setDepartamentoPai(dep);
					filtro.setSubdepartamento(null);
				}
			} else if (grupo.getNome().equals("SPACO MOTOS")) {
				if (filtro.getVisibilidade().equalsIgnoreCase("Restrita")
						|| !StringUtils.isNotBlank(filtro.getVisibilidade())) {
					Departamento dep = compromissos
							.porDepartamento("Spaço Motos");
					filtro.setDepartamentoPai(dep);
					filtro.setSubdepartamento(null);
				}
			}

		}

	}

}
