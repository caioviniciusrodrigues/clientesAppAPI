package br.com.caio.clientsapp.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.caio.clientsapp.exception.UsuarioCadastradoExcepetion;
import br.com.caio.clientsapp.model.Usuario;
import br.com.caio.clientsapp.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvar( @RequestBody @Valid Usuario usuario ) {		
		try {
			String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(senhaCriptografada);
			usuarioService.salvar(usuario);		
		} catch (UsuarioCadastradoExcepetion e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
