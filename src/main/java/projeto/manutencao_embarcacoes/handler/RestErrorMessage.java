package projeto.manutencao_embarcacoes.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {

	private LocalDateTime timestamp = LocalDateTime.now();
	private HttpStatus status;
	private String mensagem;

	public RestErrorMessage(HttpStatus status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
