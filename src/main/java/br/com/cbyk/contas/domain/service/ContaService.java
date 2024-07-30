package br.com.cbyk.contas.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import br.com.cbyk.contas.domain.model.ContaEntity;
import br.com.cbyk.contas.domain.repository.ContaRepository;
import jakarta.transaction.Transactional;

@Service
public class ContaService {

	private final ContaRepository repository;

	@Autowired
	public ContaService(final ContaRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public ContaResponse cadastrarConta(final ContaPayload payload) {

		ContaEntity entidade = ContaMapper.toEntity(payload);

		ContaEntity contaSalva = repository.save(entidade);

		return ContaMapper.toResponse(contaSalva);
	}

	public ContaResponse consultarContaPorId(Long id) {
		Optional<ContaEntity> contaResponse = retornaContaPorId(id);

		return ContaMapper.toResponse(contaResponse.get());
	}

	private Optional<ContaEntity> retornaContaPorId(Long id) {
		Optional<ContaEntity> contaResponse = repository.findById(id);

		if (!contaResponse.isPresent()) {
			throw new ContaNaoEncontradaException();
		}

		return contaResponse;
	}

	public ContaResponse atualizarSituacaoPorId(Long id, SituacaoContaPayload situacao) {
		Optional<ContaEntity> contaResponse = retornaContaPorId(id);

		ContaEntity contaEntity = contaResponse.get();

		contaEntity.setSituacao(situacao.getSituacao());

		ContaEntity contaSalva = repository.save(contaEntity);

		return ContaMapper.toResponse(contaSalva);

	}

}
