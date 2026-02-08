package projeto.manutencao_embarcacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.manutencao_embarcacoes.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
