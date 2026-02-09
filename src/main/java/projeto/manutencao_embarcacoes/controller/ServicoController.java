package projeto.manutencao_embarcacoes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.manutencao_embarcacoes.dto.ServicoDto;
import projeto.manutencao_embarcacoes.model.Servico;
import projeto.manutencao_embarcacoes.service.ServicoService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

	private final ServicoService servicoService;

	public ServicoController(ServicoService servicoService) {
		this.servicoService = servicoService;
	}

	@PostMapping("/criar")
	public ResponseEntity<Servico> salvar(@RequestBody ServicoDto servico) {
		return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.salvar(servico));
	}

	@GetMapping
	public List<Servico> listar() {
		return servicoService.listar();
	}

}
