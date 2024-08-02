package br.com.cbyk.builders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.cbyk.contas.domain.enums.Situacao;
import br.com.cbyk.contas.domain.model.ContaEntity;

public class ContaEntityBuilder {

	private ContaEntity dataToMock;

	private ContaEntityBuilder() {
		this.dataToMock = ContaEntity.builder()
				.id(1L)
				.dataPagamento(LocalDate.of(2024, 8, 1))
				.dataVencimento(LocalDate.of(2024, 8, 1))
				.descricao("TESTE")
				.valor(BigDecimal.valueOf(10))
				.situacao(Situacao.PAGO)
				.build();
	}

	public static ContaEntityBuilder create() {
		return new ContaEntityBuilder();
	}

	public Optional<ContaEntity> getContaEntityOptional() {
		return  Optional.of(this.dataToMock);
	}
	
	public ContaEntity getContaEntity() {
		return this.dataToMock;
	}
	
	public List<ContaEntity> getContaEntityList(){
		
		List<ContaEntity> list = new  ArrayList<>();
		list.add(this.dataToMock);
		
		return list;
	}

}
