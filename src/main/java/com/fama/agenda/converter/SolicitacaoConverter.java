package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Solicitacao;
import com.fama.agenda.repository.Solicitacoes;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(forClass = Solicitacao.class)
public class SolicitacaoConverter implements Converter {


	private Solicitacoes solicitacoes;

	public SolicitacaoConverter() {
		solicitacoes = CDIServiceLocator.getBean(Solicitacoes.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Solicitacao retorno = null;

		if (value != null) {
			Long id = new Long(value);
			retorno = solicitacoes.porId(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Solicitacao solicitacao = (Solicitacao) value;
		if (value != null) {
			return (solicitacao.getId() == null ? null : solicitacao.getId().toString());
		}

		return "";
	}

}
