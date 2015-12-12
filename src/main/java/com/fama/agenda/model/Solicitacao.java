package com.fama.agenda.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "solicitacoes")
public class Solicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataCriacao;
	private Date dataPrimeiraCriacao;
	private Date tempoSolicitado;
	private Date dataSolicitada;
	private String motivo;
	private Usuario colaborador;
	private String situacao;
	private String comentario;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="dt_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="dt_primeira_criacao")
	public Date getDataPrimeiraCriacao() {
		return dataPrimeiraCriacao;
	}
	
	public void setDataPrimeiraCriacao(Date dataPrimeiraCriacao) {
		this.dataPrimeiraCriacao = dataPrimeiraCriacao;
	}

	@Temporal(TemporalType.TIME)
	@Column(name="tempo_solicitado")
	public Date getTempoSolicitado() {
		return tempoSolicitado;
	}

	public void setTempoSolicitado(Date tempoSolicitado) {
		this.tempoSolicitado = tempoSolicitado;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="data_solicitada")
	public Date getDataSolicitada() {
		return dataSolicitada;
	}

	public void setDataSolicitada(Date dataSolicitada) {
		this.dataSolicitada = dataSolicitada;
	}

	@Column(name="motivo", columnDefinition="text")
	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	
	@NotNull
	@ManyToOne
	@JoinColumn(name="colaborador_id", nullable=false)
	public Usuario getColaborador() {
		return colaborador;
	}

	public void setColaborador(Usuario colaborador) {
		this.colaborador = colaborador;
	}
	
	@Size(max=10)
	@Column(name="situacao", length=10)
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	@Column(columnDefinition="text")
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solicitacao other = (Solicitacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
