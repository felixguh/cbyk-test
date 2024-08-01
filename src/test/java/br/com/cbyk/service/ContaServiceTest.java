package br.com.cbyk.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.cbyk.builders.AtualizarContaPayloadBuilder;
import br.com.cbyk.builders.ContaEntityBuilder;
import br.com.cbyk.builders.ContaPayloadBuilder;
import br.com.cbyk.builders.SituacaoContaPayloadBuilder;
import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.payload.AtualizarContaPayload;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.enums.Situacao;
import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import br.com.cbyk.contas.domain.model.ContaEntity;
import br.com.cbyk.contas.domain.repository.ContaRepository;
import br.com.cbyk.contas.domain.service.ContaService;

public class ContaServiceTest {

	@Mock
	private ContaRepository repository;

	@InjectMocks
	private ContaService contaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldReturnValue() {
		Long id = 1L;

		Optional<ContaEntity> response = ContaEntityBuilder.create().getContaEntityOptional();

		Mockito.when(repository.findById(id)).thenReturn(response);

		contaService.consultarContaPorId(id);

		Mockito.verify(repository, atLeastOnce()).findById(id);
	}

	@Test
	void whenTryToGetANonExistsValueShouldReturnContaNaoEncontradaException() {
		Long id = 1L;

		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ContaNaoEncontradaException.class, () -> {
			contaService.consultarContaPorId(id);
		});
	}

	@Test
	void whenTryCreateContaShouldReturnSuccess() {

		ContaPayload payload = ContaPayloadBuilder.create().createContaPayload();

		ContaEntity entity = ContaMapper.toEntity(payload);

		Mockito.when(repository.save(entity)).thenReturn(entity);

		ContaResponse response = contaService.cadastrarConta(payload);

		Assertions.assertEquals(Situacao.PAGO, entity.getSituacao());

		validationsPayload(payload, entity, response);

	}

	@Test
	void whenTryToCreateContaWithNullPaymentDateTheSituationShouldBeEmAberto() {

		ContaPayload payload = ContaPayloadBuilder.create().withDataPagamento(null).createContaPayload();

		ContaEntity entity = ContaMapper.toEntity(payload);

		Mockito.when(repository.save(entity)).thenReturn(entity);

		ContaResponse response = contaService.cadastrarConta(payload);

		Assertions.assertEquals(Situacao.EM_ABERTO, entity.getSituacao());

		validationsPayload(payload, entity, response);

	}

	private void validationsPayload(ContaPayload payload, ContaEntity contaSalva, ContaResponse response) {
		Assertions.assertEquals(payload.getDataPagamento(), contaSalva.getDataPagamento());
		Assertions.assertEquals(payload.getDataVencimento(), contaSalva.getDataVencimento());
		Assertions.assertEquals(payload.getDescricao(), contaSalva.getDescricao());
		Assertions.assertEquals(payload.getValor(), contaSalva.getValor());

		Assertions.assertEquals(response.getIdConta(), contaSalva.getId());
		Assertions.assertEquals(response.getDataPagamento(), contaSalva.getDataPagamento());
		Assertions.assertEquals(response.getDataVencimento(), contaSalva.getDataVencimento());
		Assertions.assertEquals(response.getDescricao(), contaSalva.getDescricao());
		Assertions.assertEquals(response.getValor(), contaSalva.getValor());
	}

	@Test
	void whenGetAmountShouldReturnSuccess() {

		LocalDate dataInicio = LocalDate.of(2024, 8, 1);
		LocalDate dataFim = LocalDate.of(2024, 8, 1);

		repository.findTotalByDateRange(dataInicio, dataFim);

		Mockito.when(repository.findTotalByDateRange(dataInicio, dataFim)).thenReturn(BigDecimal.valueOf(100));

		contaService.consultaValorTotalPorPeriodo(dataInicio, dataFim);

		Mockito.verify(repository, atLeastOnce()).findTotalByDateRange(dataInicio, dataFim);
	}

	@Test
	void shouldUpdateSituationById() {
		Long id = 1L;

		ContaEntity entity = theSame(id);

		SituacaoContaPayload payload = SituacaoContaPayloadBuilder.create().getSituacaoContaPayload();
		ContaResponse response = contaService.atualizarSituacaoPorId(id, payload);

		theSameValidations(id, entity, response);

		Assertions.assertEquals(payload.getSituacao(), entity.getSituacao());

	}

	private void theSameValidations(Long id, ContaEntity entity, ContaResponse response) {
		Mockito.verify(repository, atLeastOnce()).findById(id);

		Assertions.assertEquals(response.getSituacao(), entity.getSituacao());
	}

	@Test
	void shouldUpdateContaById() {
		Long id = 1L;

		ContaEntity entity = theSame(id);

		AtualizarContaPayload payload = AtualizarContaPayloadBuilder.create().getAtualizarContaPayload();

		ContaResponse response = contaService.atualizarContaPorId(id, payload);

		theSameValidations(id, entity, response);

	}

	private ContaEntity theSame(Long id) {
		Optional<ContaEntity> contaOptional = ContaEntityBuilder.create().getContaEntityOptional();

		Mockito.when(repository.findById(id)).thenReturn(contaOptional);

		ContaEntity entity = contaOptional.get();

		Mockito.when(repository.save(entity)).thenReturn(entity);

		return entity;
	}
}
