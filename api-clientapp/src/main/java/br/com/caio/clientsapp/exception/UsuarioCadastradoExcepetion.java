package br.com.caio.clientsapp.exception;

public class UsuarioCadastradoExcepetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioCadastradoExcepetion(String login) {
		super("Usuário já cadastrado para o login: " + login);
	}
}
