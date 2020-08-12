package br.com.caio.clientsapp.config.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.caio.clientsapp.ApiClientappApplication;
import br.com.caio.clientsapp.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chave}")
	private String chaveAssinatura;
	
	public String gerarToken(Usuario usuario) {
		Long expString = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracaoToken = LocalDateTime.now().plusMinutes(expString);
		Date data = Date.from(dataHoraExpiracaoToken.atZone(ZoneId.systemDefault()).toInstant());
		return Jwts
				.builder()
				.setSubject( usuario.getUsername() )
				.setExpiration( data )
				.signWith( SignatureAlgorithm.HS512, chaveAssinatura )
				.compact();
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(ApiClientappApplication.class, args);
		
		JwtService jwt = contexto.getBean(JwtService.class);
		Usuario usuario = new Usuario();
		usuario.setUsername("Caio");
		String token = jwt.gerarToken(usuario);
		System.out.println(token);
	}
}
