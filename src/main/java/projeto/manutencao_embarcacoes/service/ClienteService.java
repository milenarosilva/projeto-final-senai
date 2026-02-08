package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.ClienteRecordDto;
import projeto.manutencao_embarcacoes.model.Cliente;
import projeto.manutencao_embarcacoes.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public Cliente salvar(ClienteRecordDto clienteRecordDto) {
		Cliente cliente = new Cliente();
		cliente.setNome(clienteRecordDto.nome());
		cliente.setCnpj(clienteRecordDto.cnpj());
		cliente.setNumeroContato(clienteRecordDto.numeroContato());

		return clienteRepository.save(cliente);
	}

	public List<Cliente> listar(String nome) {
		if (nome != null) {
			return clienteRepository.findByNomeContainingIgnoreCase(nome);
		}
		return clienteRepository.findAll();
	}

}
