package br.com.cbyk.contas.application.mapper;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cbyk.contas.application.payload.AtualizarContaPayload;
import br.com.cbyk.contas.application.payload.ContaCsvPayload;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.commons.FormatarValores;
import br.com.cbyk.contas.domain.enums.Situacao;
import br.com.cbyk.contas.domain.model.ContaEntity;

public class ContaMapper {

	private ContaMapper() {
		throw new UnsupportedOperationException();
	}

	public static ContaEntity toEntity(ContaPayload payload) {

		Situacao situacao = (isNull(payload.getDataPagamento()) ? Situacao.EM_ABERTO : Situacao.PAGO);

		return ContaEntity.builder().dataPagamento(payload.getDataPagamento())
				.dataVencimento(payload.getDataVencimento()).descricao(payload.getDescricao()).valor(payload.getValor())
				.situacao(situacao).build();
	}

	public static ContaEntity toEntityUpdate(ContaEntity entity, AtualizarContaPayload payload) {

		Situacao situacao = (isNull(payload.getDataPagamento()) ? Situacao.EM_ABERTO : Situacao.PAGO);

		entity.setDataPagamento(payload.getDataPagamento());
		entity.setDataVencimento(payload.getDataVencimento());
		entity.setDescricao(payload.getDescricao());
		entity.setValor(payload.getValor());
		entity.setSituacao(situacao);

		return entity;
	}

	public static ContaResponse toResponse(ContaEntity entity) {

		return ContaResponse.builder().dataPagamento(entity.getDataPagamento())
				.dataVencimento(entity.getDataVencimento()).descricao(entity.getDescricao()).idConta(entity.getId())
				.situacao(entity.getSituacao()).valor(entity.getValor()).build();
	}

	public static ContaPayload toPayload(ContaCsvPayload payload) {

		LocalDate dataPagamento = FormatarValores.formatDate(payload.getDataVencimento());

		LocalDate dataVencimento = FormatarValores.formatDate(payload.getDataVencimento());

		BigDecimal valor = FormatarValores.formatBigDecimal(payload.getValor());

		return ContaPayload.builder().dataPagamento(dataPagamento).dataVencimento(dataVencimento).valor(valor)
				.descricao(payload.getDescricao()).build();
	}

}
