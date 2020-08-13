package br.com.caio.clientsapp.rest.dto;

public class TokenDTO {

	private String username;
	private String token;

	public TokenDTO() {

	}

	public TokenDTO(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
