package projeto.manutencao_embarcacoes.dto;

public record OrdemDeServicoDto(
		Long embarcacaoId,
		Long funcionarioId,
		Long servicoId,
		String observacoes) {

}
