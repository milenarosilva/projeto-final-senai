package projeto.manutencao_embarcacoes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.manutencao_embarcacoes.dto.FuncionarioRecordDto;
import projeto.manutencao_embarcacoes.model.Funcionario;
import projeto.manutencao_embarcacoes.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

	private final FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	@PostMapping("/criar")
	public ResponseEntity<Funcionario> salvar(@RequestBody FuncionarioRecordDto funcionario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.salvar(funcionario));
	}

	@GetMapping
	public List<Funcionario> listar() {
		return funcionarioService.listar();
	}

}
