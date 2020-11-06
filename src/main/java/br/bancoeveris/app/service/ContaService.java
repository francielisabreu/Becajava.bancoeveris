package br.bancoeveris.app.service;

import br.bancoeveris.app.repository.*;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import br.bancoeveris.app.model.Conta;

@Service
public class ContaService {

	final ContaRepository _repository;

	public ContaService(ContaRepository repository) {
		_repository = repository;
	}

	// Inserir
	public void criar(Conta conta) {

		conta.setId(0L);
		_repository.save(conta);
	}

	// Obter Todos
	public List<Conta> listar() {
		return _repository.findAll();

	}

	// Obter Somente Um
	public Optional<Conta> listar(Long id) {
		return _repository.findById(id);

	}

	// Atualizar dados
	public void atualizar(Conta conta, Long id) {
		conta.setId(id);
		_repository.save(conta);
	}

	// Deletar Dados
	public void deletar(Long id) {
		_repository.deleteById(id);
	}

} 
