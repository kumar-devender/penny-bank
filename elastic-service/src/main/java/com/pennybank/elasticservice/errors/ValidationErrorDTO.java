package com.pennybank.elasticservice.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDTO {

    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
    private List<String> globalErrors = new ArrayList<>();

    public void addFieldError(String path, String message, Object rejectedValue, String code) {
        FieldErrorDTO error = new FieldErrorDTO();
        error.setField(path);
        error.setMessage(message);
        error.setRejectedValue(rejectedValue);
        error.setErrorCode(code);
        fieldErrors.add(error);
    }

    public ValidationErrorDTO addGlobalError(String message) {
        globalErrors.add(message);
        return this;
    }
}