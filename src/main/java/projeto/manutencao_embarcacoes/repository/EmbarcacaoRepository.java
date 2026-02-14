package projeto.manutencao_embarcacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Embarcacao;

public interface EmbarcacaoRepository extends JpaRepository<Embarcacao, Long> {

	boolean existsByNomeIgnoreCase(String nome);

	boolean existsByNomeIgnoreCaseAndClienteId(String nome, Long id);

	Embarcacao findByNomeIgnoreCaseAndClienteId(String nome, Long id);

	List<Embarcacao> findAllByClienteId(Long id);

	List<Embarcacao> findByNomeContainingIgnoreCase(String nome);

}
