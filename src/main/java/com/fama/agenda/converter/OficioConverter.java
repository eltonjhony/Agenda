package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Oficio;
import com.fama.agenda.repository.Oficios;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Oficio.class)
public class OficioConverter implements Converter {


	private Oficios oficios;

	public OficioConverter() {
		oficios = CDIServiceLocator.getBean(Oficios.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Oficio retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = oficios.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Oficio oficio = (Oficio) value;
		if (value != null) {
			return (oficio.getId() == null ? null : oficio.getId().toString());
		}

		return "";
	}

}
