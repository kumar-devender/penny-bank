package com.pennybank.loanapplication.model;

import com.pennybank.loanapplication.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationDTO extends LoanApplicationCreateDTO {
    @NotNull
    private Long id;
    @NotBlank
    private Status status;
}
