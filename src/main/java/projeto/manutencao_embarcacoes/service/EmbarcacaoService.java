package projeto.manutencao_embarcacoes.service;

import java.util.List;

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

		validarProprietario(embarcacaoRequest.proprietarioId(), null);
		validarNome(embarcacaoRequest.nome(), embarcacaoRequest.proprietarioId(), null);
		validarTamanho(embarcacaoRequest.comprimento(), null);
		validarTipo(embarcacaoRequest.tipo(), null);

		Embarcacao embarcacao = new Embarcacao();
		embarcacao.setNome(embarcacaoRequest.nome());
		embarcacao.setTipo(embarcacaoRequest.tipo());
		embarcacao.setObservacoes(embarcacaoRequest.observacoes());
		embarcacao.setComprimento(embarcacaoRequest.comprimento());
		embarcacao.setCliente(clienteRepository.findById(embarcacaoRequest.proprietarioId()).get());
		Embarcacao embarcacaoSalva = embarcacaoRepository.save(embarcacao);

		return EmbarcacaoResponse.fromEntity(embarcacaoSalva);
	}

	public List<EmbarcacaoResponse> listar() {
		return embarcacaoRepository.findAll().stream()
				.map(EmbarcacaoResponse::fromEntity).toList();
	}

	public EmbarcacaoResponse buscarPorId(Long id) {

		Embarcacao embarcacao = embarcacaoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Embarcação com id #" + id + " não foi encontrado."));

		return EmbarcacaoResponse.fromEntity(embarcacao);
	}

	public EmbarcacaoResponse atualizar(Long id, EmbarcacaoRequest embarcacaoRequest) {

		Embarcacao embarcacaoExistente = embarcacaoRepository.findById(id)
				.orElseThrow(
						() -> new IllegalArgumentException("Não foi possível atualizar Embarcação com ID não encontrado!"));

		validarProprietario(embarcacaoRequest.proprietarioId(), id);
		validarNome(embarcacaoRequest.nome(), embarcacaoRequest.proprietarioId(), id);
		validarTipo(embarcacaoRequest.tipo(), id);
		validarTamanho(embarcacaoRequest.comprimento(), id);

		embarcacaoExistente.setCliente(clienteRepository.findById(embarcacaoRequest.proprietarioId()).get());
		embarcacaoExistente.setNome(embarcacaoRequest.nome());
		embarcacaoExistente.setTipo(embarcacaoRequest.tipo());
		embarcacaoExistente.setComprimento(embarcacaoRequest.comprimento());
		embarcacaoExistente.setObservacoes(embarcacaoRequest.observacoes());
		Embarcacao embarcacaoAtualizada = embarcacaoRepository.save(embarcacaoExistente);

		return EmbarcacaoResponse.fromEntity(embarcacaoAtualizada);
	}

	public void excluir(Long id) {
		if (!embarcacaoRepository.existsById(id))
			throw new RuntimeException("Não foi possível excluir Embarcação com ID não encontrado");

		embarcacaoRepository.deleteById(id);
	}

	private void validarNome(String nome, Long proprietarioID, Long idIgnorar) {

		if (nome == null || nome.isBlank())
			throw new IllegalArgumentException("Nome da embarcação é obrigatório e precisa ser preenchido!");

		// *Cliente* não pode ter duas embarcações com o mesmo nome
		if (embarcacaoRepository.existsByNomeIgnoreCaseAndClienteId(nome, proprietarioID))
			if (embarcacaoRepository.findByNomeIgnoreCaseAndClienteId(nome, proprietarioID).getId() != idIgnorar)
				throw new EntityExistsException("Cliente já possui embarcação cadastrada com esse Nome!");
	}

	private void validarTamanho(double tamanho, Long idIgnorar) {

		FormatoUtils.validarTamanho(tamanho);

		if (tamanho <= 0)
			throw new IllegalArgumentException("Tamanho da embarcação é obrigatório e precisa ser preenchido!");

		if (tamanho < 2)// Não trabalhamos com botes kkk
			throw new IllegalArgumentException("Embarcação muito pequena para ser	cadastrada.");
	}

	private void validarProprietario(Long proprietarioId, Long idIgnorar) {

		if (proprietarioId == null)
			throw new IllegalArgumentException("Proprietário é obrigatório e precisa ser preenchido!");

		if (!clienteRepository.existsById(proprietarioId))
			throw new IllegalArgumentException("Não existe Cliente com o ID informado!");
	}

	private void validarTipo(String tipo, Long idIgnorar) {

		if (tipo == null || tipo.isBlank())
			throw new IllegalArgumentException("Tipo é obrigatório e precisa ser preenchido!");
	}

}
