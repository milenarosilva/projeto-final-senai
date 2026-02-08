package projeto.manutencao_embarcacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findByCnpj(String cnpj);

}
