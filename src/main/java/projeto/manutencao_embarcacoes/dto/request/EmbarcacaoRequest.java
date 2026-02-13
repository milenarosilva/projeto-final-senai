package projeto.manutencao_embarcacoes.dto.request;

public record EmbarcacaoRequest(
		String nome,
		double comprimento,
		String tipo,
		String observacoes,
		Long proprietarioId) {

	public String nome() {
		return nome.trim().toUpperCase();
	}

	public String tipo() {
		return tipo.trim().toUpperCase();
	}

	public String observacoes() {
		return observacoes.trim();
	}

}
