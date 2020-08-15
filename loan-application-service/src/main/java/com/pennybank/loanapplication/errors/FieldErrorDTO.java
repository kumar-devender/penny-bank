package com.pennybank.loanapplication.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorDTO {
    private String field;
    private String message;
    private Object rejectedValue;
    private String errorCode;
}
