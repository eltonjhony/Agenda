package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Contato;
import com.fama.agenda.repository.Contatos;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Contato.class)
public class ContatoConverter implements Converter {


	private Contatos contatos;

	public ContatoConverter() {
		contatos = CDIServiceLocator.getBean(Contatos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Contato retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = contatos.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Contato contato = (Contato) value;
		if (value != null) {
			return (contato.getId() == null ? null : contato.getId().toString());
		}

		return "";
	}

}
