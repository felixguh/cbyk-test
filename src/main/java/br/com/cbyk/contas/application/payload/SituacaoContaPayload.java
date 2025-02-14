package br.com.cbyk.contas.application.payload;

import java.io.Serializable;

import br.com.cbyk.contas.domain.enums.Situacao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoContaPayload implements Serializable {

	private static final long serialVersionUID = -2347794988054079221L;

	@NotNull(message = "Situação é obrigatório!")
	private Situacao situacao;

}
