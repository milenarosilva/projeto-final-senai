package projeto.manutencao_embarcacoes.dto;

public record ServicoDto(
		String nome,
		String descricao,
		double valorBase,
		boolean isEspecializado) {

}
