package br.com.cbyk.contas.domain.service;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ValidaCamposService {

	private static final String DATE_FORMATTER = "yyyy-MM-dd";

	public boolean validateBigDecimal(String value) {

		if (nonNull(value) && !value.isEmpty()) {
			try {
				new BigDecimal(value);

			} catch (NumberFormatException e) {
				return false;
			}
		}

		return true;
	}

	public boolean validateIfIsPositive(String value) {
		if (nonNull(value) && !value.isEmpty() && (validateBigDecimal(value))) {
			BigDecimal result = new BigDecimal(value);

			return (result.compareTo(BigDecimal.ZERO) > 0);
		}

		return true;
	}

	public boolean validateFormatDate(String value) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATTER);
			Date date = sdf.parse(value);

			if (!value.equals(sdf.format(date))) {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public boolean validateIfDateExists(String date) {
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMATTER);
			df.setLenient(false);

			df.parse(date);

			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean validateIfFieldHasTheCorrectSize(String value) {
		if (nonNull(value) && !value.isEmpty()) {

			return (value.length() >= 3 && value.length() <= 255);
		}

		return true;
	}

	public boolean validateIfValueIsNotNullOrEmpty(String value) {
		return (nonNull(value) && !value.isEmpty());
	}

}
