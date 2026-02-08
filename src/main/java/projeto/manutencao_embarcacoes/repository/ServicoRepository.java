package projeto.manutencao_embarcacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
