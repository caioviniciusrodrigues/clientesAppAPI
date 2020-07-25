package br.com.caio.clientsapp.util;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalUtil {

	public BigDecimal converter(String value) {
		if(value == null) {
			return null;
		}
		value = value.replace(".", "").replace(",", ".");
		return new BigDecimal(value);
	}
}
