package br.com.cbyk.contas.domain.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cbyk.contas.application.advice.FieldErrorResponse;
import br.com.cbyk.contas.application.mapper.ContaMapper;
import br.com.cbyk.contas.application.payload.ContaCsvPayload;
import br.com.cbyk.contas.application.response.ContaErrorResponse;
import br.com.cbyk.contas.domain.enums.Campo;
import br.com.cbyk.contas.domain.exceptions.CabecalhosNaoSaoIguais;
import br.com.cbyk.contas.domain.exceptions.CsvErrosEncontradosExceptions;
import br.com.cbyk.contas.domain.exceptions.QuantidadeDeCabecalhoNaoESuficienteException;

@Service
public class CsvContaService {

	private static final String HEADER = "Data Vencimento;Data Pagamento;Valor;Descricao";

	private static final String SEPARATOR_LINE = ";";

	private final ValidaCamposService validaCamposService;

	private final ContaService contaService;

	public CsvContaService(final ValidaCamposService validaCamposService, final ContaService contaService) {
		this.validaCamposService = validaCamposService;
		this.contaService = contaService;
	}

	public void receberArquivo(MultipartFile file) throws IOException {
		List<ContaCsvPayload> list = proecessarArquivoEConverterParaObjeto(file);

		List<ContaErrorResponse> campoErros = new ArrayList<>();

		validarRegistros(list, campoErros);

		if (!campoErros.isEmpty()) {
			throw new CsvErrosEncontradosExceptions(campoErros);
		}

		list.stream().map(ContaMapper::toPayload).forEach(item -> {
			contaService.cadastrarConta(item);
		});
	}

	private void validarRegistros(List<ContaCsvPayload> list, List<ContaErrorResponse> campoErros) {
		Long index = 1L;

		for (ContaCsvPayload item : list) {
			List<FieldErrorResponse> errors = new ArrayList<>();

			Campo.DATA_VENCIMENTO.validar(item.getDataVencimento(), errors, validaCamposService);
			Campo.DATA_PAGAMENTO.validar(item.getDataPagamento(), errors, validaCamposService);
			Campo.VALOR.validar(item.getValor(), errors, validaCamposService);
			Campo.DESCRICAO.validar(item.getDescricao(), errors, validaCamposService);

			if (!errors.isEmpty()) {
				ContaErrorResponse errorResponse = ContaErrorResponse.builder().errors(errors).line(index).build();

				campoErros.add(errorResponse);
			}

			index++;
		}

	}

	private List<ContaCsvPayload> proecessarArquivoEConverterParaObjeto(MultipartFile file) throws IOException {
		BufferedReader fileReader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

		String currentLine;

		List<ContaCsvPayload> sgatList = new ArrayList<>();

		boolean verificarCabecalho = true;

		while ((currentLine = fileReader.readLine()) != null) {
			String[] linhas = currentLine.split(SEPARATOR_LINE);

			if (verificarCabecalho) {
				List<String> headers = Arrays.asList(linhas);

				validarCabecalho(headers);

				verificarCabecalho = false;

				continue;
			}

			convertItemsToObject(linhas, sgatList);
		}

		return sgatList;
	}

	private void convertItemsToObject(String[] linhas, List<ContaCsvPayload> lista) {
		if (linhas.length > 0) {

			ContaCsvPayload response = ContaCsvPayload.builder().dataVencimento(getValue(linhas, 0))
					.dataPagamento(getValue(linhas, 1)).valor(getValue(linhas, 2)).descricao(getValue(linhas, 3))
					.build();

			lista.add(response);
		}
	}

	private String getValue(String[] linhas, int posicao) {

		try {
			return Optional.ofNullable(linhas[posicao]).orElse("");
		} catch (ArrayIndexOutOfBoundsException e) {

			return "";
		}

	}

	private static void validarCabecalho(List<String> headers) {
		validarQuantidadeDeCabecalhos(headers);
		validarOrderDoCabecalho(headers);
	}

	private static void validarQuantidadeDeCabecalhos(List<String> cabecalhos) {
		long quantidadeDeItens = cabecalhos.stream().count();

		long quantidadeInformada = Arrays.asList(HEADER.split(SEPARATOR_LINE, -1)).stream().count();

		if (quantidadeDeItens < quantidadeInformada) {
			throw new QuantidadeDeCabecalhoNaoESuficienteException();
		}
	}

	private static void validarOrderDoCabecalho(List<String> cabecalhos) {
		String result = cabecalhos.stream().collect(Collectors.joining(SEPARATOR_LINE));

		if (!result.equals(HEADER)) {
			throw new CabecalhosNaoSaoIguais();
		}
	}

}
