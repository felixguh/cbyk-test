package br.com.cbyk.contas.application.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cbyk.contas.application.response.ContaErrorResponse;
import br.com.cbyk.contas.domain.exceptions.CabecalhosNaoSaoIguais;
import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import br.com.cbyk.contas.domain.exceptions.CsvErrosEncontradosExceptions;
import br.com.cbyk.contas.domain.exceptions.CsvWithoutLineToProcessException;
import br.com.cbyk.contas.domain.exceptions.QuantidadeDeCabecalhoNaoESuficienteException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(ContaNaoEncontradaException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleContaNaoEncontradaException(ContaNaoEncontradaException exception,
			final HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	
	
	@ExceptionHandler(CsvWithoutLineToProcessException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleCsvWithoutLineToProcessException(CsvWithoutLineToProcessException exception,
			final HttpServletRequest request) {

		ErrorResponse response = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
				.message("Csv sem linhas para processamento!").build();

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(CabecalhosNaoSaoIguais.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleHeadersAreNotEqualException(CabecalhosNaoSaoIguais exception,
			final HttpServletRequest request) {

		ErrorResponse response = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
				.message("Cabeçalho do Csv Não Permitido!").build();

		return ResponseEntity.badRequest().body(response);

	}

	@ExceptionHandler(QuantidadeDeCabecalhoNaoESuficienteException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleAmountHeadersIsNotEnoughException(
			QuantidadeDeCabecalhoNaoESuficienteException exception, final HttpServletRequest request) {

		ErrorResponse response = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
				.message("Quantidade de itens no cabeçalhos são inválidos!").build();

		return ResponseEntity.badRequest().body(response);

	}

	@ExceptionHandler(CsvErrosEncontradosExceptions.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<List<ContaErrorResponse>> handleCsvErrosEncontradosExceptions(
			CsvErrosEncontradosExceptions exception, final HttpServletRequest request) {

		return ResponseEntity.badRequest().body(exception.getCampoErros());

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

		List<FieldErrorResponse> camposErros = ex.getFieldErrors().stream().map(error -> FieldErrorResponse.builder()
				.campo(error.getField()).mensagem(error.getDefaultMessage()).tipo(error.getCode()).build()).toList();

		ErrorResponse response = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).campoErros(camposErros)
				.message("Validação de Campos!").build();

		return ResponseEntity.badRequest().body(response);
	}

}