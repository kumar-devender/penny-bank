package com.pennybank.loanapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationCreateDTO {
    @NotNull
    private Long customerId;
    @NotNull
    @Min(6)
    private Integer duration;
    @NotNull
    @DecimalMin(value = "1.0", inclusive = false)
    private BigDecimal amount;
}
