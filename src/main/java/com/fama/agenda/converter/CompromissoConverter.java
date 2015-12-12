package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Compromisso;
import com.fama.agenda.repository.Compromissos;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Compromisso.class)
public class CompromissoConverter implements Converter {


	private Compromissos compromissos;

	public CompromissoConverter() {
		compromissos = CDIServiceLocator.getBean(Compromissos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Compromisso retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = compromissos.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Compromisso compromisso = (Compromisso) value;
		if (value != null) {
			return (compromisso.getId() == null ? null : compromisso.getId().toString());
		}

		return "";
	}

}
