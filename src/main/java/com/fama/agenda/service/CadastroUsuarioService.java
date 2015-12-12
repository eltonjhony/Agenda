package com.fama.agenda.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import com.fama.agenda.model.Grupo;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Usuarios;
import com.fama.agenda.security.Seguranca;
import com.fama.agenda.util.jpa.Transactional;

/**
 * @author Elton Jhony R. Oliveira
 */

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {

		Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());

		if (usuarioExistente != null
				&& !usuarioExistente.getId().equals(usuario.getId())) {
			throw new NegocioException("E-mail já cadastrado");
		}
		
		if(usuario.getGrupos().isEmpty()) {
			throw new NegocioException("É necessário adicionar pelo menos 1 (um) grupo.");
		}
		
		usuario.setUsuario(new Seguranca().getUsuarioAtivo());
		
		Calendar data = Calendar.getInstance();
		data.add(Calendar.HOUR_OF_DAY, -2);
		usuario.setDataCriacao(data.getTime());

		return usuarios.guardar(usuario);

	}

	public boolean validarGrupo(Grupo novoGrupo, List<Grupo> gruposAdicionados) {

		for (Grupo g : gruposAdicionados) {

			if (novoGrupo == null || novoGrupo.equals(g)) {
				throw new NegocioException("Este grupo já está adicionado.");
			}
		}

		return true;

	}

}
