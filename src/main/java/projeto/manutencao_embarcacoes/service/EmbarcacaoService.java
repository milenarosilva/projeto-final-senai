package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.EmbarcacaoDto;
import projeto.manutencao_embarcacoes.model.Embarcacao;
import projeto.manutencao_embarcacoes.repository.ClienteRepository;
import projeto.manutencao_embarcacoes.repository.EmbarcacaoRepository;

@Service
public class EmbarcacaoService {

	private final EmbarcacaoRepository embarcacaoRepository;
	private final ClienteRepository clienteRepository;

	public EmbarcacaoService(EmbarcacaoRepository embarcacaoRepository, ClienteRepository clienteRepository) {
		this.embarcacaoRepository = embarcacaoRepository;
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public Embarcacao salvar(EmbarcacaoDto embarcacaoDto) {
		Embarcacao embarcacao = new Embarcacao();
		embarcacao.setNome(embarcacaoDto.nome());
		embarcacao.setTipo(embarcacaoDto.tipo());
		embarcacao.setComprimento(embarcacaoDto.comprimento());
		embarcacao.setObservacoes(embarcacaoDto.observacoes());
		embarcacao.setCliente(clienteRepository.findById(embarcacaoDto.clienteId()).get());

		return embarcacaoRepository.save(embarcacao);
	}

	public List<Embarcacao> listar(String nome) {
		if (nome != null) {
			return embarcacaoRepository.findByNomeContainingIgnoreCase(nome);
		}
		return embarcacaoRepository.findAll();
	}

}
