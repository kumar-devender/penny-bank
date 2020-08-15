package com.pennybank.loanapplication.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennybank.loanapplication.entity.LoanApplicationEntity;
import com.pennybank.loanapplication.model.LoanApplicationMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanApplicationMessagePublisher {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void publishMessage(LoanApplicationEntity entity) {
        log.info("Publishing message loan application message for customer [{}]" + entity.getCustomerId());
        try {
            jmsTemplate.convertAndSend("loan_application_queue", objectMapper.writeValueAsString(buildLoanApplicationMessageDTO(entity)));
        } catch (JsonProcessingException e) {
            log.warn("Error in publishing message [{}]", e);
        }
    }

    private LoanApplicationMessageDTO buildLoanApplicationMessageDTO(LoanApplicationEntity entity) {
        return LoanApplicationMessageDTO.builder()
                .customerId(entity.getCustomerId())
                .loanApplicationId(entity.getId())
                .build();
    }
}
