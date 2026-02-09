package projeto.manutencao_embarcacoes.dto;

public record EmbarcacaoDto(
		String nome,
		double comprimento,
		String tipo,
		String observacoes,
		Long clienteId) {

}
