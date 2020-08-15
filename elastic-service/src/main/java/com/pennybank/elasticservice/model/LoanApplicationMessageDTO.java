package com.pennybank.elasticservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationMessageDTO {
    private Long loanApplicationId;
    private Long customerId;
}
