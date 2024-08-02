package br.com.cbyk.contas.application.response;

import java.io.Serializable;
import java.util.List;

import br.com.cbyk.contas.application.advice.FieldErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaErrorResponse implements Serializable {

	private static final long serialVersionUID = 8657007041032942473L;

	private Long line;

	private List<FieldErrorResponse> errors;

}
