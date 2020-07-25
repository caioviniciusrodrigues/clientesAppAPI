package br.com.caio.clientsapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	public LocalDate convertString(String data, String formato) {
		return  LocalDate.parse(data, DateTimeFormatter.ofPattern(formato));
	}

}
