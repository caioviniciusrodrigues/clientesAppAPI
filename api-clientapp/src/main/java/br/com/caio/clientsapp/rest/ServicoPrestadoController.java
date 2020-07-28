package br.com.caio.clientsapp.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.caio.clientsapp.model.Cliente;
import br.com.caio.clientsapp.model.ServicoPrestado;
import br.com.caio.clientsapp.repository.ClienteRepository;
import br.com.caio.clientsapp.repository.ServicoPrestadoRepository;
import br.com.caio.clientsapp.rest.dto.ServicoPrestadoDTO;
import br.com.caio.clientsapp.util.BigDecimalUtil;
import br.com.caio.clientsapp.util.DateUtil;

@RestController
@RequestMapping("/api/servicos-prestados")
public class ServicoPrestadoController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ServicoPrestadoRepository servicoPrestadoRepository;
	
	@Autowired
	private BigDecimalUtil bigDecimalUtil;
	
	@Autowired
	private DateUtil dateUtil;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado save( @Valid @RequestBody ServicoPrestadoDTO dto) {
		ServicoPrestado servicoPrestado = new ServicoPrestado();
		
		Cliente cliente = clienteRepository
								.findById(dto.getIdCliente())
								.orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não existe"));
		
		servicoPrestado.setCliente(cliente);		
		servicoPrestado.setDescricao(dto.getDescricao());
		servicoPrestado.setData(dateUtil.convertString(dto.getData(), "dd/MM/yyyy"));
		servicoPrestado.setValor(bigDecimalUtil.converter(dto.getPreco()));
		
		return servicoPrestadoRepository.save(servicoPrestado);
	}
	
	@GetMapping
	public List<ServicoPrestado> pesquisar(	
			@RequestParam(name = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(name = "mes", required = false) Integer mes
			) {
		return servicoPrestadoRepository.findByNomeClienteAndMes("%" +nome + "%", mes);
	}

}
