package projeto.manutencao_embarcacoes.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.request.EmbarcacaoRequest;
import projeto.manutencao_embarcacoes.dto.response.EmbarcacaoResponse;
import projeto.manutencao_embarcacoes.model.Embarcacao;
import projeto.manutencao_embarcacoes.repository.ClienteRepository;
import projeto.manutencao_embarcacoes.repository.EmbarcacaoRepository;
import projeto.manutencao_embarcacoes.util.FormatoUtils;

@Service
public class EmbarcacaoService {

	private final EmbarcacaoRepository embarcacaoRepository;
	private final ClienteRepository clienteRepository;

	public EmbarcacaoService(EmbarcacaoRepository embarcacaoRepository, ClienteRepository clienteRepository) {
		this.embarcacaoRepository = embarcacaoRepository;
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public EmbarcacaoResponse salvar(EmbarcacaoRequest embarcacaoRequest) {

		// Validação
		String nome = embarcacaoRequest.nome().trim().toUpperCase();
		String tipo = embarcacaoRequest.tipo().trim().toUpperCase();
		String observacoes = embarcacaoRequest.observacoes().trim();
		double comprimento = embarcacaoRequest.comprimento();
		long proprietario = embarcacaoRequest.clienteId();

		if (Stream.of(nome, tipo)
				.anyMatch(str -> str == null || str.isBlank()) ||
				Stream.of(comprimento, proprietario)
						.anyMatch(num -> num == null))
			throw new IllegalArgumentException("Campos obrigatórios precisam ser preenchidos!");

		FormatoUtils.validarTamanho(comprimento);

		if (comprimento < 2)// Não trabalhamos com botes kkk
			throw new IllegalArgumentException("Embarcação muito pequena para ser cadastrada.");

		if (!clienteRepository.existsById(proprietario))
			throw new IllegalArgumentException("Não existe Cliente com o ID informado!");

		// *Cliente* não pode ter duas embarcações com o mesmo nome
		if (embarcacaoRepository.existsByNomeIgnoreCase(nome)) {
			if (embarcacaoRepository.findAllByClienteId(proprietario).size() != 0)
				throw new EntityExistsException("Cliente já possui embarcação cadastrada com esse Nome!");
		}

		Embarcacao embarcacao = new Embarcacao();
		embarcacao.setNome(nome);
		embarcacao.setTipo(tipo);
		embarcacao.setObservacoes(observacoes);
		embarcacao.setComprimento(comprimento);
		embarcacao.setCliente(clienteRepository.findById(proprietario).get());
		Embarcacao embarcacaoSalva = embarcacaoRepository.save(embarcacao);

		return EmbarcacaoResponse.fromEntity(embarcacaoSalva);
	}

	public List<EmbarcacaoResponse> listar() {
		return embarcacaoRepository.findAll().stream()
				.map(EmbarcacaoResponse::fromEntity).toList();
	}

	public void excluir(Long id) {
		if (!embarcacaoRepository.existsById(id))
			throw new RuntimeException("Não foi possível excluir Embarcação com ID não encontrado");

		embarcacaoRepository.deleteById(id);
	}

}
