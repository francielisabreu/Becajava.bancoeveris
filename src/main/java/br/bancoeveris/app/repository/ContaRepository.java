
package br.bancoeveris.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.bancoeveris.app.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
	//listagem da conta por hash
	List<Conta> findByHash(String hash);

}