package com.fama.agenda.repository.filter;

import java.io.Serializable;
import java.util.Date;
import com.fama.agenda.model.TipoAtividade;

/**
 * @author Elton Jhony R. Oliveira
 */

public class AtividadeFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataDe;
	private Date dataAte;
	public TipoAtividade tipos;
	private String nome;

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

	public TipoAtividade getTipos() {
		return tipos;
	}
	
	public void setTipos(TipoAtividade tipos) {
		this.tipos = tipos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
