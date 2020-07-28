package br.com.caio.clientsapp.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.caio.clientsapp.model.Cliente;
import br.com.caio.clientsapp.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final  ClienteRepository repository;
	
	@Autowired
	public ClienteController(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save( @Valid @RequestBody Cliente cliente) {
		return repository.save(cliente);
	}
	
	@GetMapping("/{id}")
	public Cliente findById(@PathVariable Long id) {
		return repository
				.findById(id)
				.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long id) {
		repository
		.findById(id)
		.map( cliente -> {
			repository.delete(cliente);
			return Void.TYPE;
		})
		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("/{id}")
	public void update( @Valid @PathVariable Long id, @RequestBody Cliente clienteAtuaizado) {
		repository
		.findById(id)
		.map( cliente -> {
			clienteAtuaizado.setId(cliente.getId());
			cliente.setCpf(clienteAtuaizado.getCpf());
			cliente.setNome(clienteAtuaizado.getNome());
			return repository.save(clienteAtuaizado);
		})
		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
