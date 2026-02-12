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
import projeto.manutencao_embarcacoes.util.FormatoUtils;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public ClienteResponse salvar(ClienteRequest clienteRequest) {

		// Validação
		String digitosCnpj = FormatoUtils.isolarNumeros(clienteRequest.cnpj());
		String razaoSocialFormatada = clienteRequest.razaoSocial().trim().toUpperCase();
		String digitosNumeroContato = FormatoUtils.isolarNumeros(clienteRequest.numeroContato());

		if (Stream.of(digitosCnpj, razaoSocialFormatada, digitosNumeroContato)
				.anyMatch(str -> str == null || str.isBlank()))
			throw new IllegalArgumentException("Campos obrigatórios precisam ser preenchidos!");

		FormatoUtils.validarDocumentoCnpj(digitosCnpj);
		FormatoUtils.validarNumeroContato(digitosNumeroContato);

		if (clienteRepository.existsByCnpj(digitosCnpj))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse CNPJ!");

		if (clienteRepository.existsByNumeroContato(digitosNumeroContato))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse Número de Contato!");

		if (clienteRepository.existsByRazaoSocialIgnoreCase(razaoSocialFormatada))
			throw new EntityExistsException("Já existe um cliente cadastrado com essa Razão Social!");

		Cliente cliente = new Cliente();
		cliente.setCnpj(digitosCnpj);
		cliente.setNumeroContato(digitosNumeroContato);
		cliente.setRazaoSocial(razaoSocialFormatada);
		Cliente clienteSalvo = clienteRepository.save(cliente);

		return ClienteResponse.fromEntity(clienteSalvo);
	}

	public List<ClienteResponse> listar() {
		return clienteRepository.findAll().stream()
				.map(ClienteResponse::fromEntity).toList();
	}

}
