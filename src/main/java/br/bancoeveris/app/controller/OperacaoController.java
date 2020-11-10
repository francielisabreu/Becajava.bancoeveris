package br.bancoeveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.service.OperacaoService;

@RestController
@RequestMapping("/operacoes")
public class OperacaoController extends BaseController {

	// PROPRIEDADES
	private final OperacaoService _service;

	// CONSTRUTOR
	public OperacaoController(OperacaoService service) {
		_service = service;
	}

	// POST - FAZ DEPOSITO E SAQUE
	@PostMapping(path = "/depositoSaque")
	public ResponseEntity criar(@RequestBody OperacaoRequest operacaoRequest) {
		try {
			BaseResponse response = _service.criar(operacaoRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	// POST - FAZ TRANSFERENCIA
	@PostMapping(path = "/transferencia")
	public ResponseEntity transferencia(@RequestBody TransferenciaRequest transferenciaRequest) {
		try {
			BaseResponse response = _service.transferencia(transferenciaRequest);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}
}