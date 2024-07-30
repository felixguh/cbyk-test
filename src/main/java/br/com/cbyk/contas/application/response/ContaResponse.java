package br.com.cbyk.contas.application.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cbyk.contas.domain.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaResponse implements Serializable {

	private static final long serialVersionUID = 5777586450367857618L;

	private Long idConta;

	private Situacao situacao;

	private LocalDate dataVencimento;

	private LocalDate dataPagamento;

	private String descricao;

	private BigDecimal valor;

}
