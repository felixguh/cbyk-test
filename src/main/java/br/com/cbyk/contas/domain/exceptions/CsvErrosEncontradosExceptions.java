package br.com.cbyk.contas.domain.exceptions;

import java.util.List;

import br.com.cbyk.contas.application.response.ContaErrorResponse;
import lombok.Getter;

@Getter
public class CsvErrosEncontradosExceptions extends RuntimeException {

	private static final long serialVersionUID = -4668120702503195581L;

	private final List<ContaErrorResponse> campoErros;

	public CsvErrosEncontradosExceptions(final List<ContaErrorResponse> campoErros) {
		super();
		this.campoErros = campoErros;
	}

}
