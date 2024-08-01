package br.com.cbyk.contas.domain.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.payload.AtualizarContaPayload;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import br.com.cbyk.contas.domain.model.ContaEntity;
import br.com.cbyk.contas.domain.repository.ContaRepository;
import br.com.cbyk.contas.domain.repository.PesquisaContaRepository;
import jakarta.transaction.Transactional;

@Service
public class ContaService {

	private final ContaRepository repository;

	private final PesquisaContaRepository pesquisaContaRepository;

	@Autowired
	public ContaService(final ContaRepository repository, final PesquisaContaRepository pesquisaContaRepository) {
		this.repository = repository;
		this.pesquisaContaRepository = pesquisaContaRepository;
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

	public ContaResponse atualizarContaPorId(Long id, AtualizarContaPayload payload) {
		Optional<ContaEntity> contaResponse = retornaContaPorId(id);

		ContaEntity contaExistente = contaResponse.get();

		ContaEntity contaParaAtualizar = ContaMapper.toEntityUpdate(contaExistente, payload);

		ContaEntity contaAtualizada = repository.save(contaParaAtualizar);

		return ContaMapper.toResponse(contaAtualizada);
	}

	public Page<ContaResponse> search(LocalDate dataVencimento, String descricao, Pageable pageable) {

		return pesquisaContaRepository.search(dataVencimento, descricao, pageable);
	}

}
