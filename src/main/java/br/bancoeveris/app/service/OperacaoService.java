package br.bancoeveris.app.service;

import java.util.Optional;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.OperacaoRepository;

public class OperacaoService {

	final OperacaoRepository _repository;

	public OperacaoService(OperacaoRepository repository) {
		_repository = repository;
	}

	// Inserir
	public void criar(Operacao operacao) {
		operacao.setId(0L);
		_repository.save(operacao);
	}

	// Obter Somente Um
	public Optional<Operacao> listar(Long id) {
		return _repository.findById(id);

	}

}
