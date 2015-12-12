package com.fama.agenda.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fama.agenda.model.Usuario;

@Named
@RequestScoped
public class Seguranca {

	@Inject
	private ExternalContext externalContext;

	public String getNomeUsuario() {
		String nome = null;

		UsuarioSistema usuarioLogado = getUsuarioLogado();

		if (usuarioLogado != null) {
			nome = usuarioLogado.getUsuario().getNome();
		}

		return nome;
	}

	public Usuario getUsuarioAtivo() {
		UsuarioSistema usuario = getUsuarioLogado();
		return usuario.getUsuario();
	}

	@Produces
	@UsuarioLogado
	private UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}

		return usuario;
	}
	
	public boolean isEmitirSolicitacaoPermitido() {
		return externalContext.isUserInRole("SUPER USUARIO") 
				|| externalContext.isUserInRole("ADMINISTRACAO");
	}
	
	public boolean isResponderSolicitacaoPermitido() {
		return externalContext.isUserInRole("SUPER USUARIO")
				|| externalContext.isUserInRole("ADMINISTRACAO");
	}

}
