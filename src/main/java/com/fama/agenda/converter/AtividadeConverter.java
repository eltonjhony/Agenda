package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Atividade;
import com.fama.agenda.repository.Atividades;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Atividade.class)
public class AtividadeConverter implements Converter {


	private Atividades atividades;

	public AtividadeConverter() {
		atividades = CDIServiceLocator.getBean(Atividades.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Atividade retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = atividades.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Atividade atividade = (Atividade) value;
		if (value != null) {
			return (atividade.getId() == null ? null : atividade.getId().toString());
		}

		return "";
	}

}
