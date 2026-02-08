package projeto.manutencao_embarcacoes.dto;

public record EmbarcacaoRecordDto(
		String nome,
		double comprimento,
		String tipo,
		String observacoes,
		Long clienteId) {

}
