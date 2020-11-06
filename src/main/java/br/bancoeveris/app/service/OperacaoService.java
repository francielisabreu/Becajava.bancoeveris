package br.bancoeveris.app.service;

import java.util.Optional;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.OperacaoRepository;

public class OperacaoService {
	
	final OperacaoRepository _repository;

	public OperacaoService(OperacaoRepository repository) {
		_repository = repository;
	}
	
	// Obter Somente Um	
	public Optional<Operacao> listar(Long id) {
		return _repository.findById(id);

	}

}
