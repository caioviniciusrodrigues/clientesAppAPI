package br.com.caio.clientsapp.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Page<Contato> list( 
			@RequestParam(value = "page", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina ) 
	{
		PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina);
		return contatoRepository.findAll(pageRequest);
	}
	
	@PatchMapping("{id}/favorito")
	public void favorite( @PathVariable Long id ) {
		Optional<Contato> contato = contatoRepository.findById(id);
		contato.ifPresent( c -> {
			boolean favorito = c.getFavorito() == Boolean.TRUE;
			c.setFavorito(!favorito);
			contatoRepository.save(c);
		});
	}
	
	@PutMapping("{id}/foto")
	public byte[] addPhoto( @PathVariable Long id, @RequestParam("foto") Part arquivo /* Part interface para leitura IO */ ) {
		Optional<Contato> contato = contatoRepository.findById(id);
		
		return contato.map( c -> {
			try {
				InputStream is = arquivo.getInputStream(); // ler o arquivo 
				byte[] bytes = new byte[(int) arquivo.getSize()]; //transforma em array de bytes
				IOUtils.readFully(is, bytes);  // le a entrada e o array
				c.setFoto(bytes);
				contatoRepository.save(c);
				is.close();
				return bytes;
			} catch (IOException e) {
				return null;
			}
		}).orElse( null );
		
	}
	
	
}
