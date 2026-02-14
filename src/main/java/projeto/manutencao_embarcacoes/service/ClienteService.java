package projeto.manutencao_embarcacoes.service;

import java.util.List;
import java.util.Map;

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

		validarCnpj(clienteRequest.cnpj(), null);
		validarNumeroContato(clienteRequest.numeroContato(), null);
		validarRazaoSocial(clienteRequest.razaoSocial(), null);

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

	public ClienteResponse buscarPorId(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente com id #" + id + " não foi encontrado."));

		return ClienteResponse.fromEntity(cliente);
	}

	@Transactional
	public ClienteResponse atualizar(Long id, ClienteRequest clienteRequest) {

		Cliente clienteExistente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Não foi possível atualizar Cliente com ID não encontrado!"));

		validarCnpj(clienteRequest.cnpj(), id);
		validarNumeroContato(clienteRequest.numeroContato(), id);
		validarRazaoSocial(clienteRequest.razaoSocial(), id);

		clienteExistente.setRazaoSocial(clienteRequest.razaoSocial());
		clienteExistente.setCnpj(clienteRequest.cnpj());
		clienteExistente.setNumeroContato(clienteRequest.numeroContato());
		Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

		return ClienteResponse.fromEntity(clienteAtualizado);
	}

	@Transactional
	public ClienteResponse atualizarSomente(Long id, Map<String, Object> campos) {

		Cliente clienteExistente = clienteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Não foi possível atualizar Cliente com ID não encontrado!"));

		// WARN: antes da validação do campo, estou fazendo manualmente o que
		// ClienteRequest faz sozinho, porque não sei como passar ClienteRequest no Map
		// ou outra forma de escolher os campos individualmente.

		campos.forEach((campo, valor) -> {
			try {
				switch (campo) {
					case "cnpj":
						String cnpj = FormatoUtils.isolarNumeros(valor.toString());
						validarCnpj(cnpj, id);
						clienteExistente.setCnpj(cnpj);
						break;

					case "razaoSocial":
						String razaoSocial = valor.toString().trim().toUpperCase();
						validarRazaoSocial(razaoSocial, id);
						clienteExistente.setRazaoSocial(razaoSocial);
						break;

					case "numeroContato":
						String numeroContato = FormatoUtils.isolarNumeros(valor.toString());
						validarNumeroContato(numeroContato, id);
						clienteExistente.setNumeroContato(numeroContato);
						break;

					default:
						throw new IllegalArgumentException("Não foi possível atualizar campo '" + campo + "'.");
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Valor '" + valor + "' não permitido em campo '" + campo + "'.");
			}
		});

		Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

		return ClienteResponse.fromEntity(clienteAtualizado);
	}

	public void excluir(Long id) {

		if (!clienteRepository.existsById(id))
			throw new IllegalArgumentException("Não foi possível excluir Cliente com ID não encontrado!");

		if (embarcacaoRepository.findAllByClienteId(id).size() != 0)
			throw new IllegalArgumentException("Não é possível excluir Cliente com embarcações cadastradas!");

		clienteRepository.deleteById(id);
	}

	// Funções de validação
	private void validarCnpj(String cnpj, Long idIgnorar) {

		FormatoUtils.validarDocumentoCnpj(cnpj);

		if (cnpj == null || cnpj.isBlank())
			throw new IllegalArgumentException("CPF é obrigatório e precisa ser preenchido!");

		if (clienteRepository.existsByCnpjAndIdNot(cnpj, idIgnorar))
			throw new IllegalArgumentException("Já existe um Cliente cadastrado com esse CNPJ!");
	}

	private void validarNumeroContato(String numeroContato, Long idIgnorar) {

		FormatoUtils.validarNumeroContato(numeroContato);

		if (numeroContato == null || numeroContato.isBlank())
			throw new IllegalArgumentException("Número Contato é obrigatório e precisa ser preenchido!");

		if (clienteRepository.existsByNumeroContatoAndIdNot(numeroContato, idIgnorar))
			throw new EntityExistsException("Já existe um cliente cadastrado com esse Número de Contato!");
	}

	private void validarRazaoSocial(String razaoSocial, Long idIgnorar) {

		if (razaoSocial == null || razaoSocial.isBlank())
			throw new IllegalArgumentException("Razão Social é obrigatória e precisa ser preenchida!");

		if (clienteRepository.existsByRazaoSocialIgnoreCaseAndIdNot(razaoSocial, idIgnorar))
			throw new EntityExistsException("Já existe um cliente cadastrado com essa Razão Social!");
	}

}
