package br.com.cbyk.builders;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cbyk.contas.application.payload.AtualizarContaPayload;

public class AtualizarContaPayloadBuilder {
	
	private AtualizarContaPayload dataToMock;
	
	private AtualizarContaPayloadBuilder() {
		this.dataToMock = AtualizarContaPayload.builder()
				.dataPagamento(LocalDate.of(2024, 8, 1))
				.dataVencimento(LocalDate.of(2024, 8, 1))
				.descricao("TESTE")
				.valor(BigDecimal.valueOf(10)).build();
	}
	
	public static AtualizarContaPayloadBuilder create() {
		return new AtualizarContaPayloadBuilder();
	}
	
	
	public AtualizarContaPayload getAtualizarContaPayload() {
		return this.dataToMock;
	}
}
