package br.com.caio.clientsapp.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.caio.clientsapp.config.security.jwt.JwtService;
import br.com.caio.clientsapp.exception.SenhaInvalidaException;
import br.com.caio.clientsapp.exception.UsuarioCadastradoExcepetion;
import br.com.caio.clientsapp.model.entity.Usuario;
import br.com.caio.clientsapp.rest.dto.CredenciaisDTO;
import br.com.caio.clientsapp.rest.dto.TokenDTO;
import br.com.caio.clientsapp.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtService jwtService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void salvar( @RequestBody @Valid Usuario usuario ) {		
		try {			
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuarioService.salvar(usuario);		
		} catch (UsuarioCadastradoExcepetion e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            
        	Usuario usuario = new Usuario(credenciais.getUsername(), credenciais.getPassword());                               
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);            
            String token = jwtService.gerarToken(usuario);
            
            return new TokenDTO(usuario.getUsername(), token);
            
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
