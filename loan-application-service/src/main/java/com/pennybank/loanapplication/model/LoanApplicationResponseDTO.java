package com.pennybank.loanapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponseDTO {
    private CustomerDTO customer;
    private List<LoanApplicationDTO> loans;
}
