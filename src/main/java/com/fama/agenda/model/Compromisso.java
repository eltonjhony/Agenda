package com.fama.agenda.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Elton Jhony R. Oliveira
 */

@Entity
@Table(name = "compromissos")
public class Compromisso implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Usuario usuario;
	private Date dataCriacao;
	private Date dataCompromisso;
	private Date horario;
	private String descricao;
	private String local;
	private String observacao;
	private Departamento departamento;
	private Departamento subDepartamento;
	private List<FormaContato> formaContato = new ArrayList<FormaContato>();
	private List<Contato> contato = new ArrayList<Contato>();
	private String visualizacao = "";
	private Visibilidade visibilidade = Visibilidade.RESTRITA;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// @NotNull
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_compromisso")
	public Date getDataCompromisso() {
		return dataCompromisso;
	}

	public void setDataCompromisso(Date dataCompromisso) {
		this.dataCompromisso = dataCompromisso;
	}

	@NotNull
	@Temporal(TemporalType.TIME)
	@Column(name = "horario")
	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	@Size(max = 200)
	@Column(name = "descricao", length = 200)
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Size(max = 60)
	@Column(name = "local", length = 60)
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Column(name = "observacao", columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "departamento_pai_id")
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@ManyToOne
	@JoinColumn(name = "subdepartamento_id")
	public Departamento getSubDepartamento() {
		return subDepartamento;
	}

	public void setSubDepartamento(Departamento subDepartamento) {
		this.subDepartamento = subDepartamento;
	}

	@OneToMany(mappedBy = "compromisso", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<FormaContato> getFormaContato() {
		return formaContato;
	}

	public void setFormaContato(List<FormaContato> formaContato) {
		this.formaContato = formaContato;
	}

	@ManyToMany()
	@JoinTable(name = "compromisso_contato", joinColumns = @JoinColumn(name = "compromisso_id"), inverseJoinColumns = @JoinColumn(name = "contato_id"))
	public List<Contato> getContato() {
		return contato;
	}

	public void setContato(List<Contato> contato) {
		this.contato = contato;
	}
	

	@Enumerated(EnumType.STRING)
	@Column(name="visibilidade", length=15)
	public Visibilidade getVisibilidade() {
		return visibilidade;
	}
	
	public void setVisibilidade(Visibilidade visibilidade) {
		this.visibilidade = visibilidade;
	}
	
	@Transient
	public String getVisualizacao() {
		return visualizacao;
	}
	
	public void setVisualizacao(String visualizacao) {
		this.visualizacao = visualizacao;
	}
	
	
	@Transient
	public boolean isVisualizando() {
		return this.visualizacao.trim().equals("visualizacao");
	}
	
	@Transient
	public boolean isNaoVisualizando() {
		return !isVisualizando();
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
		Compromisso other = (Compromisso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Transient
	public boolean isListaContatosNaoVazia() {
		return !isListaContatosVazia();
	}

	@Transient
	public boolean isListaContatosVazia() {
		return getContato().isEmpty();
	}

	@Transient
	public boolean isListaFormaContatosNaoVazia() {
		return !isListaFormaContatosVazia();
	}

	@Transient
	public boolean isListaFormaContatosVazia() {
		return getFormaContato().isEmpty();
	}

}
