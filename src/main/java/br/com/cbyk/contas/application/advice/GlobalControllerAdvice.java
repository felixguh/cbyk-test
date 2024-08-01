package br.com.cbyk.contas.application.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(ContaNaoEncontradaException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleContaNaoEncontradaException(ContaNaoEncontradaException exception,
			final HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

		List<FieldErrorResponse> camposErros = ex.getFieldErrors().stream().map(error -> FieldErrorResponse.builder()
				.campo(error.getField()).mensagem(error.getDefaultMessage()).tipo(error.getCode()).build()).toList();

		ErrorResponse response = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).campoErros(camposErros)
				.message("Validação de Campos!").build();

		return ResponseEntity.badRequest().body(response);
	}

}