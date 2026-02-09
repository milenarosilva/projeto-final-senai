package projeto.manutencao_embarcacoes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.manutencao_embarcacoes.dto.OrdemDeServicoDto;
import projeto.manutencao_embarcacoes.model.OrdemDeServico;
import projeto.manutencao_embarcacoes.service.OrdemDeServicoService;

@RestController
@RequestMapping("/api/ordem-servico")
public class OrdemDeServicoController {

	private final OrdemDeServicoService ordemDeServicoService;

	public OrdemDeServicoController(OrdemDeServicoService ordemDeServicoService) {
		this.ordemDeServicoService = ordemDeServicoService;
	}

	@PostMapping("/criar")
	public ResponseEntity<OrdemDeServico> salvar(@RequestBody OrdemDeServicoDto ordemDeServico) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ordemDeServicoService.salvar(ordemDeServico));
	}

	@GetMapping
	public List<OrdemDeServico> listar() {
		return ordemDeServicoService.listar();
	}

}
