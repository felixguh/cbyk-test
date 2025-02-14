package br.com.cbyk.contas.domain.model;

import java.io.Serializable;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "conta")
public class ContaEntity implements Serializable {

	private static final long serialVersionUID = -8856284000280619368L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_vencimento", nullable = false)
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@Column(name = "situacao", nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

}
