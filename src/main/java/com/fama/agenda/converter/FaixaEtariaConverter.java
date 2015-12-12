package com.fama.agenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.fama.agenda.model.Faixa;
import com.fama.agenda.repository.FaixaEtarias;
import com.fama.agenda.util.cdi.CDIServiceLocator;

/**
 * @author Elton Jhony R. Oliveira
 */

@FacesConverter(value = "faixaConverter")
public class FaixaEtariaConverter implements Converter {


	private FaixaEtarias faixas;

	public FaixaEtariaConverter() {
		faixas = CDIServiceLocator.getBean(FaixaEtarias.class);
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {  
        if (value == null)  
            return null;  
        return faixas.porId(Long.parseLong(value)); 
    }  
  
    public String getAsString(FacesContext context, UIComponent component, Object object) throws ConverterException {  
        if (object == null)  
            return null;  
        Faixa faixa = (Faixa) object;  
        return Long.toString(faixa.getId());  
    } 

}
