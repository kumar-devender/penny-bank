package com.pennybank.elasticservice.model;

import com.pennybank.elasticservice.document.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationDTO {
    private Long id;
    private Status status;
    private Long customerId;
    private Integer duration;
    private BigDecimal amount;
}
