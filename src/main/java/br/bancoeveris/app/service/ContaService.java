package br.bancoeveris.app.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.response.ListContaResponse;
import br.bancoeveris.app.model.BaseResponse;

@Service
public class ContaService {

	final ContaRepository _repository;
	final OperacaoService _operacaoService;

	public ContaService(ContaRepository repository, OperacaoService operacaoService) {
		_repository = repository;
		_operacaoService = operacaoService;
	}

	// METODO PARA GERAR HASH
	public String randomHash() {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrst123456789";
		String newString = "";
		int tamanho = 10;
		boolean existe = true;
		Random rand = new Random();

		while (existe) {

			char[] text = new char[tamanho];
			for (int i = 0; i < tamanho; i++) {
				text[i] = caracteres.charAt(rand.nextInt(caracteres.length()));
			}
			for (int i = 0; i < text.length; i++) {
				newString += text[i];
			}

			Conta contaExiste = _repository.findByHash(newString);

			if (contaExiste != null) {
				existe = true;
			} else
				existe = false;
		}
		return newString;

	}

	// POST - CRIAR e CRIAR HASH AUTOMATICAMENTE
	public BaseResponse inserir(ContaRequest contaRequest) {
		Conta conta = new Conta();
		ContaResponse contaResponse = new ContaResponse();
		contaResponse.StatusCode = 400;

		if (contaRequest.getNome().isEmpty()) {
			contaResponse.Message = "O Nome do cliente não foi preenchido.";
			return contaResponse;
		}

		conta.setHash(randomHash());
		conta.setNome(contaRequest.getNome());

		_repository.save(conta);

		contaResponse.StatusCode = 201;
		contaResponse.Message = "Hash Gerado automaticamente na Conta criada!!";
		contaResponse.setHash(conta.getHash());
		contaResponse.setNome(conta.getNome());
		contaResponse.setId(conta.getId());

		return contaResponse;
	}

	// GET - OBTER POR UM POR ID
	public ContaResponse obter(Long id) {
		Optional<Conta> conta = _repository.findById(id);

		ContaResponse response = new ContaResponse();

		if (conta.isEmpty()) {
			response.StatusCode = 400;
			response.Message = "Id não encontrado.";
			return response;
		}

		response.setHash(conta.get().getHash());
		response.setNome(conta.get().getNome());
		response.setId(conta.get().getId());
		response.StatusCode = 200;
		response.Message = "Conta obtida com sucesso.";
		return response;
	}

	// GET - OBTER TUDO
	public ListContaResponse listar() {

		List<Conta> lista = _repository.findAll();

		ListContaResponse response = new ListContaResponse();
		response.setContas(lista);
		response.StatusCode = 200;
		response.Message = "Clientes obtidos com sucesso.";

		return response;
	}

	// PUT - ATUALIZAR POR ID
	public BaseResponse atualizar(Long id, ContaRequest contaRequest) {

		BaseResponse response = new BaseResponse();
		Conta conta = new Conta();
		response.StatusCode = 400;

		if (contaRequest.getNome().isEmpty()) {
			response.Message = "Novo nome do cliente não foi preenchido.";
			return response;
		}

		conta.setId(id);
		conta.setNome(contaRequest.getNome());

		_repository.save(conta);
		response.StatusCode = 200;
		response.Message = "Conta Atualizada com sucesso.";
		return response;

	}

	// DELETE - DELETAR POR ID
	public BaseResponse deletar(Long id) {
		BaseResponse response = new BaseResponse();

		if (id == null) {
			response.StatusCode = 400;
			response.Message = "Id de conta não existe";
			return response;
		}

		_repository.deleteById(id);

		response.StatusCode = 200;
		response.Message = "Conta Excluida com sucesso!";
		return response;
	}

	// SALDO
	public ContaResponse Saldo(String hash) {

		ContaResponse response = new ContaResponse();
		response.StatusCode = 400;

		Conta conta = _repository.findByHash(hash);

		if (conta == null) {
			response.Message = "Conta não encontrada!!";
			return response;
		}
		double saldo = _operacaoService.Saldo(conta.getId());
		response.setSaldo(saldo);
		response.setNome(conta.getNome());
		response.setHash(conta.getHash());
		response.StatusCode = 200;
		return response;
	}
}
