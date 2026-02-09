package projeto.manutencao_embarcacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import projeto.manutencao_embarcacoes.dto.OrdemDeServicoDto;
import projeto.manutencao_embarcacoes.model.OrdemDeServico;
import projeto.manutencao_embarcacoes.repository.EmbarcacaoRepository;
import projeto.manutencao_embarcacoes.repository.FuncionarioRepository;
import projeto.manutencao_embarcacoes.repository.OrdemDeServicoRepository;
import projeto.manutencao_embarcacoes.repository.ServicoRepository;

@Service
public class OrdemDeServicoService {

	private final OrdemDeServicoRepository ordemDeServicoRepository;
	private final EmbarcacaoRepository embarcacaoRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final ServicoRepository servicoRepository;

	public OrdemDeServicoService(OrdemDeServicoRepository ordemDeServicoRepository,
			EmbarcacaoRepository embarcacaoRepository, FuncionarioRepository funcionarioRepository,
			ServicoRepository servicoRepository) {
		this.ordemDeServicoRepository = ordemDeServicoRepository;
		this.embarcacaoRepository = embarcacaoRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.servicoRepository = servicoRepository;
	}

	@Transactional
	public OrdemDeServico salvar(OrdemDeServicoDto ordemDeServicoDto) {
		OrdemDeServico ordemDeServico = new OrdemDeServico();
		ordemDeServico.setObservacoes(ordemDeServicoDto.observacoes());
		ordemDeServico.setServico(servicoRepository.findById(ordemDeServicoDto.servicoId()).get());
		ordemDeServico.setEmbarcacao(embarcacaoRepository.findById(ordemDeServicoDto.embarcacaoId()).get());
		ordemDeServico.setFuncionario(funcionarioRepository.findById(ordemDeServicoDto.funcionarioId()).get());

		// Cálculo exemplo de valor completo do serviço
		// double preco =
		// (embarcacaoRepository.findById(ordemDeServicoDto.servicoId()).get().getComprimento()
		// * 1.75)
		// *
		// servicoRepository.findById(ordemDeServicoDto.servicoId()).get().getValorBase();
		//
		// ordemDeServico.setPreco(preco);

		return ordemDeServicoRepository.save(ordemDeServico);
	}

	public List<OrdemDeServico> listar() {
		return ordemDeServicoRepository.findAll();
	}

}
