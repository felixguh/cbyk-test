package br.com.cbyk.contas.application.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(ContaNaoEncontradaException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleContaNaoEncontradaException(ContaNaoEncontradaException exception,
			final HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}