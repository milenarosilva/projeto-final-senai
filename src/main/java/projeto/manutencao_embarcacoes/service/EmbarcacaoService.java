package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.EmbarcacaoRecordDto;
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
	public Embarcacao salvar(EmbarcacaoRecordDto embarcacaoRecordDto) {
		Embarcacao embarcacao = new Embarcacao();
		embarcacao.setNome(embarcacaoRecordDto.nome());
		embarcacao.setTipo(embarcacaoRecordDto.tipo());
		embarcacao.setComprimento(embarcacaoRecordDto.comprimento());
		embarcacao.setObservacoes(embarcacaoRecordDto.observacoes());
		embarcacao.setCliente(clienteRepository.findById(embarcacaoRecordDto.clienteId()).get());

		return embarcacaoRepository.save(embarcacao);
	}

	public List<Embarcacao> listar(String nome) {
		if (nome != null) {
			return embarcacaoRepository.findByNomeContainingIgnoreCase(nome);
		}
		return embarcacaoRepository.findAll();
	}

}
