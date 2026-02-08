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

import projeto.manutencao_embarcacoes.dto.ClienteRecordDto;
import projeto.manutencao_embarcacoes.model.Cliente;
import projeto.manutencao_embarcacoes.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping("/criar")
	public ResponseEntity<Cliente> salvar(@RequestBody ClienteRecordDto cliente) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvar(cliente));
	}

	@GetMapping
	public List<Cliente> listar(@RequestParam(required = false) String nome) {
		return clienteService.listar(nome);
	}

}
