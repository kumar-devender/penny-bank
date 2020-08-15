package com.pennybank.elasticservice.service;

import com.pennybank.elasticservice.document.LoanApplicationEntity;
import com.pennybank.elasticservice.client.CustomerApi;
import com.pennybank.elasticservice.client.LoanApplicationApi;
import com.pennybank.elasticservice.model.CustomerDTO;
import com.pennybank.elasticservice.model.LoanApplicationDTO;
import com.pennybank.elasticservice.model.LoanApplicationMessageDTO;
import com.pennybank.elasticservice.repository.LoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoanApplicationMessageProcessor {
    private final CustomerApi customerApi;
    private final LoanApplicationApi loanApplicationApi;
    private final LoanApplicationRepository loanApplicationRepository;

    public void process(LoanApplicationMessageDTO loanApplicationMessageDTO) {
        CustomerDTO customerDTO = customerApi.get(loanApplicationMessageDTO.getCustomerId());
        LoanApplicationDTO loanApplicationDTO = loanApplicationApi.get(loanApplicationMessageDTO.getLoanApplicationId());
        LoanApplicationEntity loanApplicationEntity = LoanApplicationEntity.builder()
                .amount(loanApplicationDTO.getAmount())
                .duration(loanApplicationDTO.getDuration())
                .status(loanApplicationDTO.getStatus())
                .id(loanApplicationDTO.getId())
                .userId(customerDTO.getUserId())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .build();
        loanApplicationRepository.save(loanApplicationEntity);
    }
}
