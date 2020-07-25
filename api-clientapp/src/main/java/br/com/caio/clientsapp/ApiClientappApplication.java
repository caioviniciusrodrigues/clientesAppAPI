package br.com.caio.clientsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.caio.clientsapp.model.Cliente;
import br.com.caio.clientsapp.repository.ClienteRepository;

@SpringBootApplication
public class ApiClientappApplication {
	

	public CommandLineRunner run(@Autowired ClienteRepository clienteRepository) {
		return args -> {
			Cliente cliente = new Cliente(1L, "Caio", "32334153893", null);
			//bean
			clienteRepository.save(cliente);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiClientappApplication.class, args);
	}
 
}
