package br.bancoeveris.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.spec.OperacaoSpec;
import br.bancoeveris.app.spec.TransferenciaSpec;

@Service
public class OperacaoService {

	final OperacaoRepository _repository;
	final ContaRepository _contaRepository;

	// @Autowired
	public OperacaoService(OperacaoRepository repository, ContaRepository contaRepository) {
		_repository = repository;
		_contaRepository = contaRepository;
	}

	public double Saldo(Long contaId) {
		// saldo inicia com zero
		double saldo = 0;

		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);

		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);

		List<Operacao> listaOrigem = _repository.findByContaOrigem(contaOrigem);
		List<Operacao> listaDestino = _repository.findByContaDestino(contaDestino);

		for (Operacao o : listaOrigem) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":
				saldo -= o.getValor();
				break;
			default:
				break;
			}
		}

		for (Operacao o : listaDestino) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":
				saldo += o.getValor();
				break;
			default:
				break;
			}
		}
		return saldo;
	}

	// OPERACAO SAQUE E DEPOSITO
	public BaseResponse criar(OperacaoSpec operacaoSpec) {

		BaseResponse base = new BaseResponse();
		Operacao operacao = new Operacao();

		List<Conta> lista = _contaRepository.findByHash(operacaoSpec.getHash());
		if (lista.size() == 0) {
			base.StatusCode = 404;
			base.Message = "Conta não encontrada";
			return base;
		}

		operacao.setTipo(operacaoSpec.getTipo());
		operacao.setValor(operacaoSpec.getValor());

		switch (operacaoSpec.getTipo()) {
		case "D":
			operacao.setContaDestino(lista.get(0));
			break;
		case "S":
			operacao.setContaOrigem(lista.get(0));
			break;
		}

		_repository.save(operacao);
		base.Message = "Operação Realizada";
		base.StatusCode = 200;
		return base;
	}

	// transf
	public BaseResponse transferencia(TransferenciaSpec transferenciaSpec) {

		BaseResponse base = new BaseResponse();
		Operacao operacao = new Operacao();

		List<Conta> origem = _contaRepository.findByHash(transferenciaSpec.getHashOrigem());
		List<Conta> destino = _contaRepository.findByHash(transferenciaSpec.getHashDestino());

		if (origem.size() == 0) {
			base.StatusCode = 404;
			base.Message = "Conta origem não encontrada";
			return base;
		}
		if (destino.size() == 0) {
			base.StatusCode = 404;
			base.Message = "Conta destino não encontrada";
			return base;
		}

		operacao.setTipo("T");
		operacao.setContaOrigem(origem.get(0));
		operacao.setContaDestino(destino.get(0));
		operacao.setValor(transferenciaSpec.getValor());

		_repository.save(operacao);
		base.Message = "Operação Realizada";
		base.StatusCode = 200;
		return base;
	}
}