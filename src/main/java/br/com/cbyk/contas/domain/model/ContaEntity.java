package br.com.cbyk.contas.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cbyk.contas.domain.enums.Situacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "conta")
public class ContaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "situacao")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

}
