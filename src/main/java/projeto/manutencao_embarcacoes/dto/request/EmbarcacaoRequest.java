package projeto.manutencao_embarcacoes.dto.request;

public record EmbarcacaoRequest(
		String nome,
		double comprimento,
		String tipo,
		String observacoes,
		Long clienteId) {

}
