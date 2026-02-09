package projeto.manutencao_embarcacoes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projeto.manutencao_embarcacoes.dto.EmbarcacaoDto;
import projeto.manutencao_embarcacoes.model.Embarcacao;
import projeto.manutencao_embarcacoes.service.EmbarcacaoService;

@RestController
@RequestMapping("/api/embarcacoes")
public class EmbarcacaoController {

	private final EmbarcacaoService embarcacaoService;

	public EmbarcacaoController(EmbarcacaoService embarcacaoService) {
		this.embarcacaoService = embarcacaoService;
	}

	@PostMapping("/criar")
	public ResponseEntity<Embarcacao> salvar(@RequestBody EmbarcacaoDto embarcacao) {
		return ResponseEntity.status(HttpStatus.CREATED).body(embarcacaoService.salvar(embarcacao));
	}

	@GetMapping
	public List<Embarcacao> listar(@RequestParam(required = false) String nome) {
		return embarcacaoService.listar(nome);
	}

}
