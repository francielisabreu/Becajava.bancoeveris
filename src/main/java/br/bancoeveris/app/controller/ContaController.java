package br.bancoeveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.bancoeveris.app.service.ContaService;
import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.spec.ContaSpec;
import br.bancoeveris.app.spec.ContaList;

@RestController
@RequestMapping("/contas")
public class ContaController extends BaseController {

	private final ContaService _service;

	public ContaController(ContaService service) {
		_service = service;
	}
	// criar conta
	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaSpec contaSpec) {
		try {
			BaseResponse response = _service.inserir(contaSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// pegar pela hash identificadora
	@GetMapping(path = "/{hash}")
	public ResponseEntity listar(@PathVariable String hash) {
		try {
			Conta conta = _service.obterByHash(hash);
			return ResponseEntity.status(conta.StatusCode).body(conta);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// GET - OBTER TUDO
	@GetMapping
	public ResponseEntity listar() {
		try {
			ContaList contas = _service.listar();
			return ResponseEntity.status(contas.StatusCode).body(contas);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// PUT - ATUALIZAR
	@PutMapping(path = "/{hash}")
	public ResponseEntity atualizar(@RequestBody ContaSpec contaSpec, @PathVariable String hash) {
		try {
			BaseResponse response = _service.atualizar(hash, contaSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// DELETE - DELETAR
	@DeleteMapping(path = "/{hash}")
	public ResponseEntity deletar(@PathVariable String hash) {
		try {
			BaseResponse response = _service.deletar(hash);
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