package com.pennybank.elasticservice.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ValidationErrorDTO handleNotFound(NotFoundException e) {
        log.debug(
                "Encountered not found exception while processing request, responding with [{}]: [{}]",
                NOT_FOUND,
                e.getMessage());
        return new ValidationErrorDTO().addGlobalError(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = CONFLICT)
    public ValidationErrorDTO handleRequestValidationErrors(MethodArgumentNotValidException e) {
        log.debug("Encountered validation exception while processing request, responding with [{}]", CONFLICT, e);
        BindingResult result = e.getBindingResult();
        return processFieldErrors(result.getFieldErrors());
    }

    @ExceptionHandler(MalFormedJsonException.class)
    @ResponseStatus(value = CONFLICT)
    public ValidationErrorDTO handleRequestValidationErrors(MalFormedJsonException e) {
        log.debug("Encountered validation exception while processing request, responding with [{}]", CONFLICT, e);
        return new ValidationErrorDTO().addGlobalError(e.getMessage());
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError : fieldErrors) {
            dto.addFieldError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue(),
                    fieldError.getCode()
            );
        }
        return dto;
    }

}
