package br.com.cbyk.contas.application.advice;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldErrorResponse implements Serializable {

	private static final long serialVersionUID = -120966826134429405L;

	private String campo;

	private String mensagem;

	private String tipo;

}
