package projeto.manutencao_embarcacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByCnpj(String cnpj);

	boolean existsByNumeroContato(String numeroContato);

	boolean existsByRazaoSocialIgnoreCase(String razaoSocial);

	Cliente findByCnpj(String cnpj);

	List<Cliente> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

}
