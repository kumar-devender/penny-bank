package com.pennybank.customer.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("The entity you are looking for does not exist");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
