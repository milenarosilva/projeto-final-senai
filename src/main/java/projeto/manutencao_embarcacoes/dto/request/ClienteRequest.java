package projeto.manutencao_embarcacoes.dto.request;

public record ClienteRequest(
		String razaoSocial,
		String cnpj,
		String numeroContato) {

}
