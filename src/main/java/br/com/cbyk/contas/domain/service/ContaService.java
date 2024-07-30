package br.com.cbyk.contas.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cbyk.contas.domain.repository.ContaRepository;

@Service
public class ContaService {

	private final ContaRepository repository;

	@Autowired
	public ContaService(final ContaRepository repository) {
		this.repository = repository;

	}

}
