package br.com.cbyk.contas.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {

	private final ContaService service;

	@Autowired
	public ContaController(final ContaService service) {
		this.service = service;

	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContaResponse criarConta(@RequestBody @Validated ContaPayload payload) {

		return service.cadastrarConta(payload);
	}

}
