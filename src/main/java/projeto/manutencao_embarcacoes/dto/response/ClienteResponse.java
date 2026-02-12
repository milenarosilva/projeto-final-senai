package projeto.manutencao_embarcacoes.dto.response;

import projeto.manutencao_embarcacoes.model.Cliente;
import projeto.manutencao_embarcacoes.util.FormatoUtils;

public record ClienteResponse(
		Long id,
		String razaoSocial,
		String numeroContato,
		String cnpj) {

	public static ClienteResponse fromEntity(Cliente cliente) {
		return new ClienteResponse(
				cliente.getId(),
				cliente.getRazaoSocial(),
				FormatoUtils.formatarNumeroContato(cliente.getNumeroContato()),
				FormatoUtils.formatarDocumento(cliente.getCnpj()));
	}

}
