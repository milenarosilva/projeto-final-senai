package projeto.manutencao_embarcacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.OrdemDeServico;

public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long> {

}
