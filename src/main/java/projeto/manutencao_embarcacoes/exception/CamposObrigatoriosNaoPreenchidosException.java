package projeto.manutencao_embarcacoes.exception;

public class CamposObrigatoriosNaoPreenchidosException extends IllegalArgumentException {

	public CamposObrigatoriosNaoPreenchidosException() {
		super("Campos obrigatórios precisam ser preenchidos!");
	}

	public CamposObrigatoriosNaoPreenchidosException(String campo) {
		super("Campo '" + campo + "' é obrigatório e precisa ser preenchido!");
	}

}
