package projeto.manutencao_embarcacoes.dto.request;

import projeto.manutencao_embarcacoes.util.FormatoUtils;

public record ClienteRequest(
		String razaoSocial,
		String cnpj,
		String numeroContato) {

	public String cnpj() {
		return FormatoUtils.isolarNumeros(cnpj);
	}

	public String razaoSocial() {
		return razaoSocial.trim().toUpperCase();
	}

	public String numeroContato() {
		return FormatoUtils.isolarNumeros(numeroContato);
	}

}
