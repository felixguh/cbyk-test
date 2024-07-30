package br.com.cbyk.contas.application.mapper;

import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.model.ContaEntity;

public class ContaMapper {

	private ContaMapper() {
		throw new UnsupportedOperationException();
	}

	public static ContaEntity toEntity(ContaPayload payload) {

		return ContaEntity.builder().dataPagamento(payload.getDataPagamento())
				.dataVencimento(payload.getDataVencimento()).descricao(payload.getDescricao()).valor(payload.getValor())
				.build();
	}

	public static ContaResponse toResponse(ContaEntity entity) {

		return ContaResponse.builder().dataPagamento(entity.getDataPagamento())
				.dataVencimento(entity.getDataVencimento()).descricao(entity.getDescricao()).idConta(entity.getId())
				.situacao(entity.getSituacao()).build();
	}

}
