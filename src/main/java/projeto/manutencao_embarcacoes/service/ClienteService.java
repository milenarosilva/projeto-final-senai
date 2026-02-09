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
		cliente.setRazaoSocial(clienteRecordDto.razaoSocial());
		cliente.setCnpj(clienteRecordDto.cnpj());
		cliente.setNumeroContato(clienteRecordDto.numeroContato());

		return clienteRepository.save(cliente);
	}

	public List<Cliente> listar(String razaoSocial) {
		if (razaoSocial != null) {
			return clienteRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);
		}
		return clienteRepository.findAll();
	}

}
