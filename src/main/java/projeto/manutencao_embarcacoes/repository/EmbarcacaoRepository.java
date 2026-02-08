package projeto.manutencao_embarcacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Embarcacao;

public interface EmbarcacaoRepository extends JpaRepository<Embarcacao, Long> {

	List<Embarcacao> findByNomeContainingIgnoreCase(String nome);

}
