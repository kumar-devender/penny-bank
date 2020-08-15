package com.pennybank.elasticservice.listener;

import com.pennybank.elasticservice.service.LoanApplicationMessageProcessor;
import com.pennybank.elasticservice.model.LoanApplicationMessageDTO;
import com.pennybank.elasticservice.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanApplicationMessageListener {
    private final JsonHelper jsonHelper;
    private final LoanApplicationMessageProcessor messageProcessor;

    @JmsListener(destination = "loan_application_queue", containerFactory = "loanApplicationListenerFactory")
    public void receiveMessage(Message message) {
        log.info("Received loan application message {{}}", message);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String messageData = textMessage.getText();
                LoanApplicationMessageDTO loanApplicationMessageDTO = jsonHelper.fromJson(messageData, LoanApplicationMessageDTO.class);
                messageProcessor.process(loanApplicationMessageDTO);
                log.info("Processed message for loan application [{}]", loanApplicationMessageDTO.getLoanApplicationId());
            } catch (JMSException e) {
                log.warn("Can not convert message to LoanApplicationMessageDTO {}", e);
            }
        }
    }
}
