package br.bancoeveris.app.service;

import java.util.List;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;

@Service
public class OperacaoService {

	// PROPRIEDADES
	final OperacaoRepository _repository;
	final ContaRepository _contaRepository;

	public OperacaoService(OperacaoRepository repository, ContaRepository contaRepository) {
		_repository = repository;
		_contaRepository = contaRepository;
	}

	// CALCULA O SALDO AO DAR GET CONTA
	public double Saldo(Long contaId) {

		double saldo = 0;

		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);

		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);

		List<Operacao> lista = _repository.findOperacoesPorConta(contaId);

		for (Operacao o : lista) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":

				if (contaId == o.getContaOrigem().getId())
					saldo -= o.getValor();

				if (contaId == o.getContaDestino().getId())
					saldo += o.getValor();
				break;

			default:
				break;
			}
		}

		return saldo;
	}

	// OPERACAO SAQUE E DEPOSITO
	public BaseResponse criar(OperacaoRequest operacaoRequest) {

		BaseResponse base = new BaseResponse();
		Operacao operacao = new Operacao();

		Conta conta = _contaRepository.findByHash(operacaoRequest.getHash());
		if (conta == null) {
			base.StatusCode = 404;
			base.Message = "Conta não encontrada";
			return base;
		}

		operacao.setTipo(operacaoRequest.getTipo());
		operacao.setValor(operacaoRequest.getValor());

		switch (operacaoRequest.getTipo()) {
		case "D":
			operacao.setContaDestino(conta);
			break;
		case "S":
			operacao.setContaOrigem(conta);
			break;
		}

		_repository.save(operacao);
		base.Message = "Operação Realizada";
		base.StatusCode = 200;
		return base;
	}

	// OPERACAO TRANSFERENCIA
	public BaseResponse transferencia(TransferenciaRequest transferenciaRequest) {

		BaseResponse base = new BaseResponse();
		Operacao operacao = new Operacao();

		Conta origem = _contaRepository.findByHash(transferenciaRequest.getHashOrigem());
		Conta destino = _contaRepository.findByHash(transferenciaRequest.getHashDestino());

		if (origem == null) {
			base.StatusCode = 404;
			base.Message = "Conta origem não encontrada";
			return base;
		}
		if (destino == null) {
			base.StatusCode = 404;
			base.Message = "Conta destino não encontrada";
			return base;
		}

		operacao.setTipo("T");
		operacao.setContaOrigem(origem);
		operacao.setContaDestino(destino);
		operacao.setValor(transferenciaRequest.getValor());

		_repository.save(operacao);
		base.Message = "Operação Realizada";
		base.StatusCode = 200;
		return base;
	}
}
