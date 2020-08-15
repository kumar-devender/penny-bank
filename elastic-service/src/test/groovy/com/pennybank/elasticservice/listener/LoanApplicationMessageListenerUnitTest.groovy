package com.pennybank.elasticservice.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.pennybank.elasticservice.model.LoanApplicationMessageDTO
import com.pennybank.elasticservice.service.LoanApplicationMessageProcessor
import com.pennybank.elasticservice.util.JsonHelper
import org.apache.activemq.command.ActiveMQTextMessage
import spock.lang.Specification

class LoanApplicationMessageListenerUnitTest extends Specification {
    private LoanApplicationMessageListener applicationMessageListener
    private JsonHelper jsonHelper = new JsonHelper(new ObjectMapper())
    private LoanApplicationMessageProcessor messageProcessor = Mock(LoanApplicationMessageProcessor)

    void setup() {
        applicationMessageListener = new LoanApplicationMessageListener(jsonHelper, messageProcessor)
    }

    def 'message process should be successful'() {
        given:
        def loanId = 10L
        def customerId = 20L
        def message = buildMessage(loanId, customerId)
        when:
        applicationMessageListener.receiveMessage(message)
        then:
        1 * messageProcessor.process(_ as LoanApplicationMessageDTO)
    }

    def buildMessage(Long loanId, Long customerId) {
        def message = new ActiveMQTextMessage()
        message.setText(jsonHelper.toJson(new LoanApplicationMessageDTO(loanId, customerId)))
        return message
    }

}
