package projeto.manutencao_embarcacoes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.manutencao_embarcacoes.dto.request.ClienteRequest;
import projeto.manutencao_embarcacoes.dto.response.ClienteResponse;
import projeto.manutencao_embarcacoes.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping("/criar")
	public ResponseEntity<ClienteResponse> criar(@RequestBody @Validated ClienteRequest clienteRequest) {
		ClienteResponse clienteResponse = clienteService.salvar(clienteRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
	}

	@GetMapping
	public ResponseEntity<List<ClienteResponse>> listar() {
		List<ClienteResponse> clienteResponse = clienteService.listar();
		return ResponseEntity.ok(clienteResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest) {
		ClienteResponse clienteResponse = clienteService.atualizar(id, clienteRequest);
		return ResponseEntity.ok(clienteResponse);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClienteResponse> atualizarSomente(@PathVariable Long id,
			@RequestBody Map<String, Object> campos) {
		ClienteResponse clienteResponse = clienteService.atualizarSomente(id, campos);
		return ResponseEntity.ok(clienteResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
