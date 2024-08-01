package br.com.cbyk.contas.application.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cbyk.contas.application.payload.AtualizarContaPayload;
import br.com.cbyk.contas.application.payload.ContaPayload;
import br.com.cbyk.contas.application.payload.SituacaoContaPayload;
import br.com.cbyk.contas.application.response.ContaResponse;
import br.com.cbyk.contas.domain.model.ContaEntity;
import br.com.cbyk.contas.domain.service.ContaService;
import br.com.cbyk.contas.domain.service.CsvContaService;
import jakarta.validation.Valid;

@RequestMapping("/contas")
@RestController
public class ContaController {

	private final ContaService service;

	private final CsvContaService csvContaService;

	@Autowired
	public ContaController(final ContaService service, final CsvContaService csvContaService) {
		this.service = service;
		this.csvContaService = csvContaService;
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

	@PostMapping("/upload")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void uploadFileTwo(@RequestParam("file") MultipartFile file) throws Exception {

		csvContaService.receberArquivo(file);
	}

	@GetMapping
	public Page<ContaEntity> pesquisar(@RequestParam(required = false) LocalDate dataVencimento,
			@RequestParam(required = false) String descricao, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);

		return service.pesquisar(dataVencimento, descricao, pageable);
	}

	@GetMapping("/total")
	public BigDecimal pesquisaERetornaOTotal(@RequestParam(required = true, name = "dataInicio") LocalDate dataInicio,
			@RequestParam(required = true, name = "dataFim") LocalDate dataFim) {

		return service.consultaValorTotalPorPeriodo(dataInicio, dataFim);
	}

}
