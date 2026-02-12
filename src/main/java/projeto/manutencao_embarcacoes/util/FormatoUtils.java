package projeto.manutencao_embarcacoes.util;

public final class FormatoUtils {

	public FormatoUtils() {
		// Caso a classe seja chamada sem método
		throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada");
	}

	public static String isolarNumeros(String string) {
		return string != null ? string.replaceAll("[^\\d]", "") : null;
	}

	public static String formatarNumeroContato(String numeroContato) {
		if (numeroContato == null || numeroContato.isBlank())
			return null;

		numeroContato = isolarNumeros(numeroContato);

		switch (numeroContato.length()) {
			case 11:
				return String.format("(%s) %s-%s",
						numeroContato.substring(0, 2),
						numeroContato.substring(2, 7),
						numeroContato.substring(7, 11));

			case 10:
				return String.format("(%s) %s-%s",
						numeroContato.substring(0, 2),
						numeroContato.substring(2, 6),
						numeroContato.substring(6, 10));

			default:
				return null;
		}

	}

	public static String formatarDocumento(String documento) {
		if (documento == null || documento.isBlank())
			return null;

		documento = isolarNumeros(documento);

		switch (documento.length()) {
			case 11: // CPF
				return String.format("%s.%s.%s-%s",
						documento.substring(0, 3),
						documento.substring(3, 6),
						documento.substring(6, 9),
						documento.substring(9, 11));

			case 14: // CNPJ
				return String.format("%s.%s.%s/%s-%s",
						documento.substring(0, 2),
						documento.substring(2, 5),
						documento.substring(5, 8),
						documento.substring(8, 12),
						documento.substring(12, 14));

			default:
				return null;
		}
	}

	// NOTE: essas validações só conferem quantidade de dígitos pois o programa não
	// pede mais complexidade :p

	public static void validarDocumentoCnpj(String cnpj) {
		cnpj = isolarNumeros(cnpj);

		if (cnpj.length() != 14)
			throw new IllegalArgumentException("Campo espera receber CNPJ");
	}

	public static void validarDocumentoCpf(String cpf) {
		cpf = isolarNumeros(cpf);

		if (cpf.length() != 11)
			throw new IllegalArgumentException("Campo espera receber CPF");
	}

	public static void validarNumeroContato(String numeroContato) {
		numeroContato = isolarNumeros(numeroContato);

		if (numeroContato.length() != 11 && numeroContato.length() != 10)
			throw new IllegalArgumentException("Campo espera formato DDD+número!");
	}

}
