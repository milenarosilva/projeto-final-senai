package projeto.manutencao_embarcacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByCnpj(String cnpj);

	boolean existsByCnpjAndIdNot(String cnpj, Long idIgnorar);

	boolean existsByNumeroContato(String numeroContato);

	boolean existsByNumeroContatoAndIdNot(String numeroContato, Long idIgnorar);

	boolean existsByRazaoSocialIgnoreCase(String razaoSocial);

	boolean existsByRazaoSocialIgnoreCaseAndIdNot(String razaoSocial, Long idIgnorar);

	Cliente findByCnpj(String cnpj);

	List<Cliente> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

}
