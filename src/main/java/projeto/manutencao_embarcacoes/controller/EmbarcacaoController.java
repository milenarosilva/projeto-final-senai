package projeto.manutencao_embarcacoes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.manutencao_embarcacoes.dto.request.EmbarcacaoRequest;
import projeto.manutencao_embarcacoes.dto.response.EmbarcacaoResponse;
import projeto.manutencao_embarcacoes.service.EmbarcacaoService;

@RestController
@RequestMapping("/api/embarcacoes")
public class EmbarcacaoController {

	private final EmbarcacaoService embarcacaoService;

	public EmbarcacaoController(EmbarcacaoService embarcacaoService) {
		this.embarcacaoService = embarcacaoService;
	}

	@PostMapping("/criar")
	public ResponseEntity<EmbarcacaoResponse> salvar(@RequestBody EmbarcacaoRequest embarcacaoRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(embarcacaoService.salvar(embarcacaoRequest));
	}

	@GetMapping
	public ResponseEntity<List<EmbarcacaoResponse>> listar() {
		List<EmbarcacaoResponse> embarcacaoResponse = embarcacaoService.listar();
		return ResponseEntity.ok(embarcacaoResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmbarcacaoResponse> buscarPorId(@PathVariable Long id) {
		EmbarcacaoResponse embarcacaoResponse = embarcacaoService.buscarPorId(id);
		return ResponseEntity.ok(embarcacaoResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmbarcacaoResponse> atualizar(@PathVariable Long id,
			@RequestBody EmbarcacaoRequest embarcacaoRequest) {
		EmbarcacaoResponse embarcacaoResponse = embarcacaoService.atualizar(id, embarcacaoRequest);
		return ResponseEntity.ok(embarcacaoResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		embarcacaoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
