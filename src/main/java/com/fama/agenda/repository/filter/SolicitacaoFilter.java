package com.fama.agenda.repository.filter;

import java.util.Date;

import com.fama.agenda.model.Usuario;

public class SolicitacaoFilter {

	private Date dataSolicitacao;
	private Date dataDe;
	private Date dataAte;
	private Usuario colaborador;
	
	

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
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

	public Usuario getColaborador() {
		return colaborador;
	}

	public void setColaborador(Usuario colaborador) {
		this.colaborador = colaborador;
	}

}
