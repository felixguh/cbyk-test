package br.com.cbyk.contas.application.advice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	private int status;
	private String message;
	private String errorType;
	private String uri;
	private List<FieldErrorResponse> campoErros;

}
