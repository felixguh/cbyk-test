package br.com.cbyk.contas.application.mapper;

import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.model.ContaEntity;

public class ContaMapper {

	private ContaMapper() {
		throw new UnsupportedOperationException();
	}

	public static ContaEntity toEntity(ContaPayload payload) {

		return null;
	}

	public static ContaResponse toResponse(ContaEntity entity) {

		return null;
	}

}
