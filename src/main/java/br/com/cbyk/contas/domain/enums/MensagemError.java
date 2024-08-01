package br.com.cbyk.contas.domain.enums;

import lombok.Getter;

@Getter
public enum MensagemError {

	CAMPO_NULO_OU_VAZIO("Campo está nulo ou vazio!"),

	FORMATO_DATA("O formato da data está inválido - Ex: yyyy-MM-dd!"),

	VALOR_BIGDECIMAL("O valor está inválido - Ex: 10.00!"),

	VALOR_POSITIVO("O valor precisa ser positivo - Ex: 10.00!");

	private String mensagem;

	MensagemError(String mensagem) {
		this.mensagem = mensagem;
	}

}
