package projeto.manutencao_embarcacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Embarcacao;

public interface EmbarcacaoRepository extends JpaRepository<Embarcacao, Long> {

}
