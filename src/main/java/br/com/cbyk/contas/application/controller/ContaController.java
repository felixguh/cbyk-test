package br.com.cbyk.contas.application.controller;

import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {

	private final ConfigurationService service;

	@Autowired
	public ContaController(final ConfigurationService service) {
		this.service = service;

	}

}
