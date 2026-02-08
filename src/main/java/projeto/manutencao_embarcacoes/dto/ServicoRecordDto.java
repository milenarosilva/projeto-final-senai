package projeto.manutencao_embarcacoes.dto;

public record ServicoRecordDto(
		String nome,
		String descricao,
		double valorBase,
		boolean isEspecializado) {

}
