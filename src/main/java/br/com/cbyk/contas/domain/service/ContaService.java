package br.com.cbyk.contas.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
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

}
