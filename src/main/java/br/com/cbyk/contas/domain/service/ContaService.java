package br.com.cbyk.contas.domain.service;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.payload.AtualizarContaPayload;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.enums.SearchOperation;
import br.com.cbyk.contas.domain.exceptions.ContaNaoEncontradaException;
import br.com.cbyk.contas.domain.model.ContaEntity;
import br.com.cbyk.contas.domain.model.predicates.ContaSpecification;
import br.com.cbyk.contas.domain.model.predicates.SearchCriteria;
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

	public ContaResponse atualizarContaPorId(Long id, AtualizarContaPayload payload) {
		Optional<ContaEntity> contaResponse = retornaContaPorId(id);

		ContaEntity contaExistente = contaResponse.get();

		ContaEntity contaParaAtualizar = ContaMapper.toEntityUpdate(contaExistente, payload);

		ContaEntity contaAtualizada = repository.save(contaParaAtualizar);

		return ContaMapper.toResponse(contaAtualizada);
	}

	public Page<ContaEntity> search(LocalDate dataVencimento, String descricao, Pageable pageable) {

		ContaSpecification contaSpecification = new ContaSpecification();

		List<ContaSpecification> listOfSpecifications = new ArrayList<>();

		if (nonNull(descricao) && !descricao.isEmpty()) {
			ContaSpecification desricaoEspecification = new ContaSpecification(contaSpecification.getCriterias());

			desricaoEspecification.add(new SearchCriteria("descricao", descricao, SearchOperation.MATCH));

			listOfSpecifications.add(desricaoEspecification);
		}

		if (nonNull(dataVencimento)) {
			ContaSpecification desricaoEspecification = new ContaSpecification(contaSpecification.getCriterias());

			desricaoEspecification.add(new SearchCriteria("dataVencimento", dataVencimento, SearchOperation.MATCH));

			listOfSpecifications.add(desricaoEspecification);
		}

		if (listOfSpecifications.size() == 2) {
			return this.repository.findAll(
					Specification.where(listOfSpecifications.get(0)).or(listOfSpecifications.get(1)), pageable);
		} else if (listOfSpecifications.size() == 1) {
			return this.repository.findAll(listOfSpecifications.get(0), pageable);
		} else {
			return this.repository.findAll(contaSpecification, pageable);
		}

	}

}
