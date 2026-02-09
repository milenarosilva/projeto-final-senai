package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.FuncionarioDto;
import projeto.manutencao_embarcacoes.model.Funcionario;
import projeto.manutencao_embarcacoes.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	private final FuncionarioRepository funcionarioRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	@Transactional
	public Funcionario salvar(FuncionarioDto funcionarioDto) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(funcionarioDto.nome());
		funcionario.setSobrenome(funcionarioDto.sobrenome());
		funcionario.setCpf(funcionarioDto.cpf());
		funcionario.setFuncao(funcionarioDto.funcao());
		funcionario.setNumeroContato(funcionarioDto.numeroContato());

		return funcionarioRepository.save(funcionario);
	}

	public List<Funcionario> listar() {
		return funcionarioRepository.findAll();
	}

}
