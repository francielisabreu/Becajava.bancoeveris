package br.bancoeveris.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.BaseResponse;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.spec.OperacaoSpec;


@Service
public class OperacaoService {

	final OperacaoRepository _repository;
	
	@Autowired
	ContaRepository contaRepository;
	
	// @Autowired
	public OperacaoService(OperacaoRepository repository) {
		_repository = repository;
	}
	
	//SALDO 
		public double Saldo(Long contaId) {
		
		double saldo = 0;
		
		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);
		
		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);
		
		List<Operacao> listaOrigem = _repository.findByContaOrigem(contaOrigem);
		List<Operacao> listaDestino = _repository.findByContaDestino(contaDestino);
		
		for(Operacao o : listaOrigem) {			
			switch(o.getTipo()) {				
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
		
		for(Operacao o : listaDestino) {			
			switch(o.getTipo()) {				
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
		
	// CRIAR OPERACAO
	public BaseResponse criar(OperacaoSpec operacaoSpec) {
		
		BaseResponse base = new BaseResponse();
		Operacao operacao = new Operacao();
		
		operacao.setContaDestino(operacaoSpec.getContaDestino());
		operacao.setContaOrigem(operacaoSpec.getContaOrigem());
		operacao.setTipo(operacaoSpec.getTipo());
		operacao.setValor(operacaoSpec.getValor());
		
		operacao.setId(0L);
		_repository.save(operacao);
		base.Message="Operação Realizada";
		base.StatusCode=200;
		return base;
	}

	
	
	
	
	
	
	
}
	