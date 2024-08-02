package br.com.cbyk.contas.application.payload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizarContaPayload implements Serializable {

	private static final long serialVersionUID = -6331822860168521260L;

	@NotNull(message = "Data de vencimento é obrigatório!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimento;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataPagamento;

	@NotNull(message = "Valor é obrigatório!")
	@Positive(message = "Valor precisa ser positivo!")
	private BigDecimal valor;

	@NotEmpty(message = "Descrição é obrigatória!")
	@Size(min = 3, max = 255, message = "Mínimo permitido {value} e Maximo permitido {value} caracteres!")
	private String descricao;

}
