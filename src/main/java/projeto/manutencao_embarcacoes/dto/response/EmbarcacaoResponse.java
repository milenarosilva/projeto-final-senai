package projeto.manutencao_embarcacoes.dto.response;

import projeto.manutencao_embarcacoes.model.Embarcacao;

public record EmbarcacaoResponse(
		Long id,
		String nome,
		String comprimento,
		String tipo,
		String observacoes,
		ClienteResponse proprietario) {

	public static EmbarcacaoResponse fromEntity(Embarcacao embarcacao) {
		return new EmbarcacaoResponse(
				embarcacao.getId(),
				embarcacao.getNome(),
				embarcacao.getComprimento() + "m",
				embarcacao.getTipo(),
				embarcacao.getObservacoes(),
				ClienteResponse.fromEntity(embarcacao.getCliente()));
	}

}
