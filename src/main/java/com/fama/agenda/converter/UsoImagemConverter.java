package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.fama.agenda.model.UsoImagem;
import com.fama.agenda.repository.Oficios;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = UsoImagem.class)
public class UsoImagemConverter implements Converter {


	private Oficios oficios;

	public UsoImagemConverter() {
		oficios = CDIServiceLocator.getBean(Oficios.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		UsoImagem retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = oficios.byId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		UsoImagem oficio = (UsoImagem) value;
		if (value != null) {
			return (oficio.getId() == null ? null : oficio.getId().toString());
		}

		return "";
	}

}
