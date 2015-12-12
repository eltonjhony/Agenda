package com.fama.agenda.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.fama.agenda.model.Recado;
import com.fama.agenda.model.Usuario;
import com.fama.agenda.repository.Recados;
import com.fama.agenda.util.jpa.Transactional;

public class CadastroRecadoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Recados recados;

	public boolean enviarEmail(List<Usuario> usuario, File arquivo, Recado recado) {

		HtmlEmail email = configurarServidordeEnvio(recado);

		try {
			email.setFrom(recado.getEmail());
			email.setDebug(true);

			email.setSubject(recado.getAssunto());
			StringBuilder builder = new StringBuilder();
			builder.append("<h1>" + recado.getTitulo() + "</h1>");
			builder.append("<p>" + recado.getDescricao() + "</p>");
			email.setMsg(builder.toString());

			for (Usuario u : usuario) {
				email.addTo(u.getEmail());
			}

			if (arquivo != null) {
				EmailAttachment anexo = anexarArquivos(arquivo);
				email.attach(anexo);
			}

			email.send();
			return true;

		} catch (EmailException e) {
			throw new NegocioException("Não foi possível enviar o E-mail. O endereço remetente está incorreto.");
		}

	}

	private EmailAttachment anexarArquivos(File arquivo) {

		/*
		 * File path = new File("src/main/resources/anexos/teste.docx")
		 * .getAbsoluteFile();
		 */

		EmailAttachment anexo = new EmailAttachment();
		anexo.setPath(arquivo.getPath());
		anexo.setDisposition(EmailAttachment.ATTACHMENT);
		anexo.setName(arquivo.getName());

		return anexo;

	}

	private HtmlEmail configurarServidordeEnvio(Recado recado) {

		HtmlEmail email = new HtmlEmail();
		email.setCharset("UTF-8");
		email.setSSL(true);
		email.setHostName("smtp.gmail.com");
		email.setSslSmtpPort("465");
		email.setAuthenticator(new DefaultAuthenticator(
				recado.getEmail(),recado.getSenha()));

		return email;

	}

	@Transactional
	public void salvar(Recado recado) {
		recados.guardar(recado);
		
	}

}
