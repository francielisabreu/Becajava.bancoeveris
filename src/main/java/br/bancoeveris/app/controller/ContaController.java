package br.bancoeveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.bancoeveris.app.service.ContaService;
import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.ContaResponse;
import br.bancoeveris.app.response.ListContaResponse;

@RestController
@RequestMapping("/contas")
public class ContaController extends BaseController {

	// PROPRIEDADES
	private final ContaService _service;

	// CONSTRUTOR
	public ContaController(ContaService service) {
		_service = service;
	}

	// POST - CRIAR
	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaRequest contaRequest) {
		try {
			BaseResponse response = _service.inserir(contaRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// GET - OBTER POR ID
	@GetMapping(path = "/{id}")
	public ResponseEntity obter(@PathVariable Long id) {
		try {
			ContaResponse contaResponse = _service.obter(id);
			return ResponseEntity.status(contaResponse.StatusCode).body(contaResponse);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// GET - OBTER TUDO
	@GetMapping
	public ResponseEntity listar() {
		try {
			ListContaResponse contas = _service.listar();
			return ResponseEntity.status(contas.StatusCode).body(contas);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// PUT - ATUALIZAR
	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ContaRequest contaRequest, @PathVariable Long id) {
		try {
			BaseResponse response = _service.atualizar(id, contaRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// DELETE - DELETAR
	@DeleteMapping(path = "/{id}")
	public ResponseEntity deletar(@PathVariable Long id) {
		try {
			BaseResponse response = _service.deletar(id);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// GET - PEGAR SALDO
	@GetMapping(path = "/saldo/{hash}")
	public ResponseEntity Saldo(@PathVariable String hash) {
		try {
			BaseResponse response = _service.Saldo(hash);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}
}
