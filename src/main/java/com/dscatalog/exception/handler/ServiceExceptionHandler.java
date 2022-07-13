package com.dscatalog.exception.handler;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dscatalog.dto.ExceptionDTO;
import com.dscatalog.exception.CategoryAtualizadaException;
import com.dscatalog.exception.CategoryCadastradaException;
import com.dscatalog.exception.DadosNaoLocalizadosException;
import com.dscatalog.exception.DeleteException;

@ControllerAdvice
public class ServiceExceptionHandler {

	@ExceptionHandler(DadosNaoLocalizadosException.class)
	public ResponseEntity<ExceptionDTO> dadosNaoLoicalizados(DadosNaoLocalizadosException e,
			HttpServletRequest request) {

		ExceptionDTO error = new ExceptionDTO();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Service not found");
		error.setMassage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

	}
	
	@ExceptionHandler(CategoryCadastradaException.class)
	public ResponseEntity<ExceptionDTO> categoriaCadastradaException(CategoryCadastradaException e,
			HttpServletRequest request) {

		ExceptionDTO error = new ExceptionDTO();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError("Solicitação inválida");
		error.setMassage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

	}
	
	@ExceptionHandler(CategoryAtualizadaException.class)
	public ResponseEntity<ExceptionDTO> categoriaCadastradaException(CategoryAtualizadaException e,
			HttpServletRequest request) {

		ExceptionDTO error = new ExceptionDTO();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError("Solicitação inválida");
		error.setMassage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

	}
	
	@ExceptionHandler(DeleteException.class)
	public ResponseEntity<ExceptionDTO> categoriaCadastradaException(DeleteException e,
			HttpServletRequest request) {

		ExceptionDTO error = new ExceptionDTO();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Solicitação inválida");
		error.setMassage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

	}


}
