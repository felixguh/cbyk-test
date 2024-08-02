package br.com.cbyk.builders;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cbyk.contas.application.payload.ContaPayload;

public class ContaPayloadBuilder {

	private ContaPayload dataToMock;

	private ContaPayloadBuilder() {
		this.dataToMock = ContaPayload.builder()
				.dataPagamento(LocalDate.of(2024, 8, 1))
				.dataVencimento(LocalDate.of(2024, 8, 1))
				.descricao("TESTE")
				.valor(BigDecimal.valueOf(10))
				.build();
	}
	
	public static ContaPayloadBuilder create() {
		return new ContaPayloadBuilder();
	}
	
	public ContaPayloadBuilder withDataPagamento(LocalDate data) {
		this.dataToMock.setDataPagamento(data);
		
		return this;
	}
	
	public ContaPayload createContaPayload() {
		return this.dataToMock;
	}

}
