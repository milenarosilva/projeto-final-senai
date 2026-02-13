package projeto.manutencao_embarcacoes.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.request.ClienteRequest;
import projeto.manutencao_embarcacoes.dto.response.ClienteResponse;
import projeto.manutencao_embarcacoes.model.Cliente;
import projeto.manutencao_embarcacoes.repository.ClienteRepository;
import projeto.manutencao_embarcacoes.repository.EmbarcacaoRepository;
import projeto.manutencao_embarcacoes.util.FormatoUtils;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final EmbarcacaoRepository embarcacaoRepository;

	public ClienteService(ClienteRepository clienteRepository, EmbarcacaoRepository embarcacaoRepository) {
		this.clienteRepository = clienteRepository;
		this.embarcacaoRepository = embarcacaoRepository;
	}

	@Transactional
	public ClienteResponse salvar(ClienteRequest clienteRequest) {

		if (Stream.of(clienteRequest.cnpj(), clienteRequest.razaoSocial(), clienteRequest.numeroContato())
				.anyMatch(str -> str == null || str.isBlank()))
			throw new IllegalArgumentException("Campos obrigatórios precisam ser preenchidos!");

		FormatoUtils.validarDocumentoCnpj(clienteRequest.cnpj());
		FormatoUtils.validarNumeroContato(clienteRequest.numeroContato());

		if (clienteRepository.existsByCnpj(clienteRequest.cnpj()))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse CNPJ!");

		if (clienteRepository.existsByNumeroContato(clienteRequest.numeroContato()))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse Número de Contato!");

		if (clienteRepository.existsByRazaoSocialIgnoreCase(clienteRequest.razaoSocial()))
			throw new EntityExistsException("Já existe um cliente cadastrado com essa Razão Social!");

		Cliente cliente = new Cliente();
		cliente.setCnpj(clienteRequest.cnpj());
		cliente.setNumeroContato(clienteRequest.numeroContato());
		cliente.setRazaoSocial(clienteRequest.razaoSocial());
		Cliente clienteSalvo = clienteRepository.save(cliente);

		return ClienteResponse.fromEntity(clienteSalvo);
	}

	public List<ClienteResponse> listar() {
		return clienteRepository.findAll().stream()
				.map(ClienteResponse::fromEntity).toList();
	}

	public void excluir(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new RuntimeException("Não foi possível excluir Cliente com ID não encontrado!");
		} else {
			if (embarcacaoRepository.findAllByClienteId(id).size() != 0)
				throw new RuntimeException("Não é possível excluir Cliente com embarcações cadastradas!");
		}

		clienteRepository.deleteById(id);
	}

}
