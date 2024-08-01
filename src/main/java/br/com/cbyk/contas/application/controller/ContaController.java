package br.com.cbyk.contas.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cbyk.contas.application.payload.AtualizarContaPayload;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.service.ContaService;
import jakarta.validation.Valid;

@RequestMapping("/contas")
@RestController
public class ContaController {

	private final ContaService service;

	@Autowired
	public ContaController(final ContaService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContaResponse criarConta(@RequestBody @Valid ContaPayload payload) {

		return service.cadastrarConta(payload);
	}

	@GetMapping("/{id}")
	public ContaResponse consultaContaPorId(@PathVariable Long id) {

		return service.consultarContaPorId(id);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ContaResponse atualizarSituacaoPorId(@PathVariable Long id,
			@RequestBody @Valid SituacaoContaPayload situacao) {

		return service.atualizarSituacaoPorId(id, situacao);
	}

	@PutMapping("/{id}")
	public ContaResponse atualizarConta(@PathVariable Long id, @RequestBody AtualizarContaPayload payload) {

		return service.atualizarContaPorId(id, payload);
	}

}
