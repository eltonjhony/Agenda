package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Grupo;
import com.fama.agenda.repository.Grupos;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

	// @Inject
	private Grupos grupos;

	public GrupoConverter() {
		grupos = CDIServiceLocator.getBean(Grupos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Grupo retorno = null;

		if (value != null) {
			System.out.println("converter saporra");
			Long id = new Long(value);
			retorno = grupos.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Grupo grupo = (Grupo) value;
		if (value != null) {
			return (grupo.getId() == null ? null : grupo.getId().toString());
		}

		return "";
	}

}
