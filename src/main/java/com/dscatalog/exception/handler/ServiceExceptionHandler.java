package com.dscatalog.exception.handler;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dscatalog.dto.ExceptionDTO;
import com.dscatalog.exception.DadosNaoLocalizadosException;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String HEADER_MESSAGE = "mensagem";
	private static final LocalDate DATE = LocalDate.now();
	private static final String CATEGORIA_NAO_LOCALIZADA_PELO_ID = "Categoria Não Localizada";
	private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

	@ExceptionHandler(DadosNaoLocalizadosException.class)
	public ResponseEntity<Object> handleIdNaoLocalizado(DadosNaoLocalizadosException e, ServletWebRequest request) {

		logger.error(e.getMessage());
		ExceptionDTO response = criarExceptionResponseDTO(DATE, CATEGORIA_NAO_LOCALIZADA_PELO_ID,
				Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}

	private ExceptionDTO criarExceptionResponseDTO(LocalDate date, String title, List<String> detail, String path) {
		ExceptionDTO exception = new ExceptionDTO();
		exception.setDetail(detail);
		exception.setPath(path);
		exception.setTitle(title);
		exception.setTimestamp(date);

		return exception;
	}

}
