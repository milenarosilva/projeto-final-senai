package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.FuncionarioRecordDto;
import projeto.manutencao_embarcacoes.model.Funcionario;
import projeto.manutencao_embarcacoes.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	private final FuncionarioRepository funcionarioRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	@Transactional
	public Funcionario salvar(FuncionarioRecordDto funcionarioRecordDto) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(funcionarioRecordDto.nome());
		funcionario.setSobrenome(funcionarioRecordDto.sobrenome());
		funcionario.setCpf(funcionarioRecordDto.cpf());
		funcionario.setFuncao(funcionarioRecordDto.funcao());
		funcionario.setNumeroContato(funcionarioRecordDto.numeroContato());

		return funcionarioRepository.save(funcionario);
	}

	public List<Funcionario> listar() {
		return funcionarioRepository.findAll();
	}

}
