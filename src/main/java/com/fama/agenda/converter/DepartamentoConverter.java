package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Departamento;
import com.fama.agenda.repository.Departamentos;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Departamento.class)
public class DepartamentoConverter implements Converter {


	private Departamentos departamentos;

	public DepartamentoConverter() {
		departamentos = CDIServiceLocator.getBean(Departamentos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Departamento retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = departamentos.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Departamento departamento = (Departamento) value;
		if (value != null) {
			return (departamento.getId() == null ? null : departamento.getId().toString());
		}

		return "";
	}

}
