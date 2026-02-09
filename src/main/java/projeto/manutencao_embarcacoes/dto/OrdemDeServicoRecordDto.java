package projeto.manutencao_embarcacoes.dto;

public record OrdemDeServicoRecordDto(
		Long embarcacaoId,
		Long funcionarioId,
		Long servicoId,
		String observacoes) {

}
