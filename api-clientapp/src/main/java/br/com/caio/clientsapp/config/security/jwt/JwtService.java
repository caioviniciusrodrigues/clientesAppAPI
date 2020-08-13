package br.com.caio.clientsapp.config.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.caio.clientsapp.model.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
		
		HashMap<String, Object> informacoes = new HashMap<>();
		informacoes.put("email", "teste@email.com");
		
		return Jwts
				.builder() // COMECA COM BUILDER PARA CONFIGURAÇÃO
				.setSubject( usuario.getUsername() )
				.setExpiration( data ) //DATA DE EXPIRAÇÃO
				//.setClaims( informacoes ) //PASSAR INFORMACOES DENTRO DO TOKEN
				.signWith( SignatureAlgorithm.HS512, chaveAssinatura ) // CHAVE PRA COMPOR O TOKEN
				.compact(); // RETORNA STRING
	}
	
	private Claims obterClaims( String token ) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean tokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime dataExpiracaoToken = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			return LocalDateTime.now().isBefore(dataExpiracaoToken);
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
	}
	
	public static void main(String[] args) {
		/*ConfigurableApplicationContext contexto = SpringApplication.run(ApiClientappApplication.class, args);		
		JwtService service = contexto.getBean(JwtService.class);
		Usuario usuario = new Usuario();
		usuario.setUsername("Caio");
		String token = service.gerarToken(usuario);
		System.out.println(token);
		
		boolean isTokenValido = service.tokenValido(token);
		System.out.println("O Token está valido? " + isTokenValido);
		
		System.out.println("Data Atual :" + LocalDateTime.now());		
		System.out.println("Data Token :" + service.obterClaims(token).getExpiration());
		
		System.out.println("Login do usuário: " + service.obterLoginUsuario(token));*/
	}
}
