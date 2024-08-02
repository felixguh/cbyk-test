package br.com.cbyk.contas.commons;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FormatarValores {

	public static LocalDate formatDate(String value) {

		try {
			return LocalDate.parse(value);

		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static BigDecimal formatBigDecimal(String value) {
		try {
			return new BigDecimal(value);

		} catch (NumberFormatException e) {
			return null;
		}

	}

}
