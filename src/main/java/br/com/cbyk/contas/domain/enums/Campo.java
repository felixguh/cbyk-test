package br.com.cbyk.contas.domain.enums;

import java.util.List;

import br.com.cbyk.contas.application.advice.FieldErrorResponse;
import br.com.cbyk.contas.domain.service.ValidaCamposService;
import lombok.Getter;

@Getter
public enum Campo {

	DATA_VENCIMENTO("data_vencimento") {
		@Override
		public void validar(String value, List<FieldErrorResponse> errors, ValidaCamposService validation) {
			if (!validation.validateIfValueIsNotNullOrEmpty(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.CAMPO_NULO_OU_VAZIO.getMensagem()).tipo(TIPO_VALIDACAO).build());
			}

			if (!validation.validateFormatDate(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.FORMATO_DATA.getMensagem()).tipo(TIPO_VALIDACAO).build());
			}
		}
	},
	DATA_PAGAMENTO("data_pagamento") {
		@Override
		public void validar(String value, List<FieldErrorResponse> errors, ValidaCamposService validation) {

			if (validation.validateIfValueIsNotNullOrEmpty(value) && !validation.validateFormatDate(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.FORMATO_DATA.getMensagem()).tipo(TIPO_VALIDACAO).build());
			}

		}
	},
	VALOR("valor") {
		@Override
		public void validar(String value, List<FieldErrorResponse> errors, ValidaCamposService validation) {
			if (!validation.validateIfValueIsNotNullOrEmpty(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.CAMPO_NULO_OU_VAZIO.getMensagem()).tipo(TIPO_VALIDACAO).build());
			}

			if (!validation.validateBigDecimal(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.VALOR_BIGDECIMAL.getMensagem()).tipo(TIPO_VALIDACAO).build());
			}

			if (!validation.validateIfIsPositive(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.VALOR_POSITIVO.getMensagem()).tipo(TIPO_VALIDACAO).build());

			}

		}

	},
	DESCRICAO("descricao") {
		@Override
		public void validar(String value, List<FieldErrorResponse> errors, ValidaCamposService validation) {
			if (!validation.validateIfValueIsNotNullOrEmpty(value)) {
				errors.add(FieldErrorResponse.builder().campo(this.getNome()).mensagem(TIPO_VALIDACAO)
						.mensagem(MensagemError.CAMPO_NULO_OU_VAZIO.getMensagem()).tipo(TIPO_VALIDACAO).build());
			}
		}

	};

	private String nome;

	private static final String TIPO_VALIDACAO = "VALIDACAO_CSV";

	Campo(String nome) {
		this.nome = nome;
	}

	public abstract void validar(String value, List<FieldErrorResponse> errors, ValidaCamposService validation);

}
