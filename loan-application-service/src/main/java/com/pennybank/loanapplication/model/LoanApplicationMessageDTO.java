package com.pennybank.loanapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanApplicationMessageDTO {
    private Long loanApplicationId;
    private Long customerId;
}
