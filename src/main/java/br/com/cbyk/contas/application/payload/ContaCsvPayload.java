package br.com.cbyk.contas.application.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaCsvPayload implements Serializable {

	private static final long serialVersionUID = -1540425520069737196L;

	private String dataVencimento;

	private String dataPagamento;

	private String valor;

	private String descricao;

}
