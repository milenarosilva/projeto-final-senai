package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.manutencao_embarcacoes.dto.ServicoRecordDto;
import projeto.manutencao_embarcacoes.model.Servico;
import projeto.manutencao_embarcacoes.repository.ServicoRepository;

@Service
public class ServicoService {

	private final ServicoRepository servicoRepository;

	public ServicoService(ServicoRepository servicoRepository) {
		this.servicoRepository = servicoRepository;
	}

	@Transactional
	public Servico salvar(ServicoRecordDto servicoRecordDto) {
		Servico servico = new Servico();
		servico.setNome(servicoRecordDto.nome());
		servico.setDescricao(servicoRecordDto.descricao());
		servico.setValorBase(servicoRecordDto.valorBase());
		servico.setEspecializado(servicoRecordDto.isEspecializado());

		return servicoRepository.save(servico);
	}

	public List<Servico> listar() {
		return servicoRepository.findAll();
	}

}
