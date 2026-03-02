package projeto.manutencao_embarcacoes.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import projeto.manutencao_embarcacoes.exception.CamposObrigatoriosNaoPreenchidosException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CamposObrigatoriosNaoPreenchidosException.class)
	private ResponseEntity<RestErrorMessage> CamposObrigatoriosNaoPreenchidosHandler(
			CamposObrigatoriosNaoPreenchidosException exception) {

		RestErrorMessage response = new RestErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@ExceptionHandler(RuntimeException.class)
	private ResponseEntity<RestErrorMessage> runtimeExceptionHandler(RuntimeException exception) {

		RestErrorMessage response = new RestErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());

		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
