package br.com.caio.clientsapp.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.caio.clientsapp.model.entity.Contato;
import br.com.caio.clientsapp.repository.ContatoRepository;

@RestController
@RequestMapping("/api/contatos")
public class ContatoController {
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Contato save( @RequestBody Contato contato ) {
		return contatoRepository.save(contato);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id ) {
		contatoRepository.deleteById(id);
	}
	
	@GetMapping
	public List<Contato> list() {
		return contatoRepository.findAll();
	}
	
	@PatchMapping("{id}/favorito")
	public void favorite( @PathVariable Long id, @RequestBody Boolean favorito ) {
		Optional<Contato> contato = contatoRepository.findById(id);
		contato.ifPresent( c -> {
			c.setFavorito(favorito);
			contatoRepository.save(c);
		});
	}
}
