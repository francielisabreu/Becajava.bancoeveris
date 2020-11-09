package br.bancoeveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.service.OperacaoService;
import br.bancoeveris.app.spec.OperacaoSpec;

@RestController
@RequestMapping("/operacoes")
public class OperacaoController extends BaseController {

	// PROPRIEDADES
	private final OperacaoService _service;

	// CONSTRUTOR
	public OperacaoController(OperacaoService service) {
		_service = service;
	}

	// POST - INSERIR
	@PostMapping
	public ResponseEntity inserir(@RequestBody OperacaoSpec operacaoSpec) {
		try {
			BaseResponse response = _service.criar(operacaoSpec);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}
}
