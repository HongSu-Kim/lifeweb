package com.bethefirst.lifeweb.config;

import com.bethefirst.lifeweb.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorResult illegalExHandler(IllegalArgumentException e){
		return new ErrorResult("IllegalArgumentException" , e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorResult BindExHandler(BindException e){
		StringBuilder message = new StringBuilder();
		e.getBindingResult().getFieldErrors().forEach(fieldError -> message.append(fieldError.getDefaultMessage()));
		return new ErrorResult("BindException" , message.toString());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ErrorResult runtimeExHandler(RuntimeException e){
		return new ErrorResult("RuntimeException" , e.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandler(Exception e){
        return new ErrorResult("Exception" , e.getMessage());
    }
	
}


