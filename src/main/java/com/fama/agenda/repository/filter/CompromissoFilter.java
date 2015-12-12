package com.fama.agenda.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.fama.agenda.model.Departamento;

/**
 * @author Elton Jhony R. Oliveira
 */

public class CompromissoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	private Departamento departamentoPai;
	private Departamento subdepartamento;
	private String visibilidade;
	private String descricao;

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}

	public Departamento getDepartamentoPai() {
		return departamentoPai;
	}

	public void setDepartamentoPai(Departamento departamentoPai) {
		this.departamentoPai = departamentoPai;
	}

	public Departamento getSubdepartamento() {
		return subdepartamento;
	}

	public void setSubdepartamento(Departamento subdepartamento) {
		this.subdepartamento = subdepartamento;
	}
	
	public String getVisibilidade() {
		return visibilidade;
	}
	
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
