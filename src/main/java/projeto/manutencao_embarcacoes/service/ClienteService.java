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

		// Validação
		String cnpj = FormatoUtils.isolarNumeros(clienteRequest.cnpj());
		String razaoSocial = clienteRequest.razaoSocial().trim().toUpperCase();
		String numeroContato = FormatoUtils.isolarNumeros(clienteRequest.numeroContato());

		if (Stream.of(cnpj, razaoSocial, numeroContato)
				.anyMatch(str -> str == null || str.isBlank()))
			throw new IllegalArgumentException("Campos obrigatórios precisam ser preenchidos!");

		FormatoUtils.validarDocumentoCnpj(cnpj);
		FormatoUtils.validarNumeroContato(numeroContato);

		if (clienteRepository.existsByCnpj(cnpj))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse CNPJ!");

		if (clienteRepository.existsByNumeroContato(numeroContato))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse Número de Contato!");

		if (clienteRepository.existsByRazaoSocialIgnoreCase(razaoSocial))
			throw new EntityExistsException("Já existe um cliente cadastrado com essa Razão Social!");

		Cliente cliente = new Cliente();
		cliente.setCnpj(cnpj);
		cliente.setNumeroContato(numeroContato);
		cliente.setRazaoSocial(razaoSocial);
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
