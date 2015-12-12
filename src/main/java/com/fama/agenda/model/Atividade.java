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

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Elton Jhony R. Oliveira
 */

@Entity
@Table(name="atividades")
public class Atividade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Usuario usuario;
	private Date dataCriacao;
	private TipoAtividade tipoAtividade = TipoAtividade.INTERNA;
	private Date dataAtividade;
	private Date horarioDiurno;
	private Date horarioVespertino;
	private Date horarioNoturno;
	private String nomeAtividade;
	private String descricao;
	private String participantes;
	private String quilometragem;
	private List<Faixa> faixas = new ArrayList<Faixa>();
	private List<Usuario> colaboradores = new ArrayList<Usuario>();
	private List<FormaContato> formaContato = new ArrayList<FormaContato>();
	private List<Contato> contato = new ArrayList<Contato>();
	private String visualizacao = "";


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="atividade_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//@NotNull
	@ManyToOne
	@JoinColumn(name="usuario_id")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_criacao", nullable=false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_atividade")
	public TipoAtividade getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(TipoAtividade tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="data_atividade", nullable=false)
	public Date getDataAtividade() {
		return dataAtividade;
	}

	public void setDataAtividade(Date dataAtividade) {
		this.dataAtividade = dataAtividade;
	}
	 
	@Temporal(TemporalType.TIME)
	@Column(name="horario_diurno")
	public Date getHorarioDiurno() {
		return horarioDiurno;
	}

	public void setHorarioDiurno(Date horarioDiurno) {
		this.horarioDiurno = horarioDiurno;
	}

	@Temporal(TemporalType.TIME)
	@Column(name="horario_vespertino")
	public Date getHorarioVespertino() {
		return horarioVespertino;
	}

	public void setHorarioVespertino(Date horarioVespertino) {
		this.horarioVespertino = horarioVespertino;
	}
	
	@Temporal(TemporalType.TIME)
	@Column(name="horario_noturno")
	public Date getHorarioNoturno() {
		return horarioNoturno;
	}
	
	public void setHorarioNoturno(Date horarioNoturno) {
		this.horarioNoturno = horarioNoturno;
	}

	@NotBlank
	@Size(max=100)
	@Column(name="nome_atividade", nullable=false, length=100)
	public String getNomeAtividade() {
		return nomeAtividade;
	}

	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}

	@Column(name="descricao", columnDefinition="text")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Size(max=200)
	@Column(name="participantes", length=200)
	public String getParticipantes() {
		return participantes;
	}

	public void setParticipantes(String participantes) {
		this.participantes = participantes;
	}

	@Size(max=3)
	@Column(name="quilometragem", length=3)
	public String getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(String quilometragem) {
		this.quilometragem = quilometragem;
	}
	
	@ManyToMany()
	@JoinTable(name = "atividade_faixa", joinColumns = @JoinColumn(name = "atividade_id"), inverseJoinColumns = @JoinColumn(name = "faixa_id"))
	public List<Faixa> getFaixas() {
		return faixas;
	}
	
	
	public void setFaixas(List<Faixa> faixas) {
		this.faixas = faixas;
	}

	@ManyToMany()
	@JoinTable(name = "atividade_usuario", joinColumns = @JoinColumn(name = "atividade_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	public List<Usuario> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Usuario> colaboradores) {
		this.colaboradores = colaboradores;
	}

	@OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<FormaContato> getFormaContato() {
		return formaContato;
	}

	public void setFormaContato(List<FormaContato> formaContato) {
		this.formaContato = formaContato;
	}

	@ManyToMany()
	@JoinTable(name = "atividade_contato", joinColumns = @JoinColumn(name = "atividade_id"), inverseJoinColumns = @JoinColumn(name = "contato_id"))
	public List<Contato> getContato() {
		return contato;
	}

	public void setContato(List<Contato> contato) {
		this.contato = contato;
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
		Atividade other = (Atividade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
