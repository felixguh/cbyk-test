package br.com.cbyk.builders;

import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.domain.enums.Situacao;

public class SituacaoContaPayloadBuilder {

	private SituacaoContaPayload dataToMock;

	public SituacaoContaPayloadBuilder() {
		this.dataToMock = SituacaoContaPayload.builder().situacao(Situacao.PAGO).build();
	}

	public static SituacaoContaPayloadBuilder create() {
		return new SituacaoContaPayloadBuilder();
	}

	public SituacaoContaPayload getSituacaoContaPayload() {
		return this.dataToMock;
	}

}
