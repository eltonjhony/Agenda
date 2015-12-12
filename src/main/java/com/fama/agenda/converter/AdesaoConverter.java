package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Adesao;
import com.fama.agenda.repository.Termos;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Adesao.class)
public class AdesaoConverter implements Converter {


	private Termos oficios;

	public AdesaoConverter() {
		oficios = CDIServiceLocator.getBean(Termos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Adesao retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = oficios.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Adesao oficio = (Adesao) value;
		if (value != null) {
			return (oficio.getId() == null ? null : oficio.getId().toString());
		}

		return "";
	}

}
