package br.com.cbyk.contas.application.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaPayload implements Serializable {

	private static final long serialVersionUID = 1938273388333605582L;

	@Nonnull
	private LocalDate dataVencimento;

	@Nonnull
	private LocalDate dataPagamento;

	@Nonnull
	private BigDecimal valor;

	@NotEmpty
	private String descricao;

}
