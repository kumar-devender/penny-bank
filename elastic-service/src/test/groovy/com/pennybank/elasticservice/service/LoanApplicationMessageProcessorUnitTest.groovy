package com.pennybank.elasticservice.service

import com.pennybank.elasticservice.client.CustomerApi
import com.pennybank.elasticservice.client.LoanApplicationApi
import com.pennybank.elasticservice.document.LoanApplicationEntity
import com.pennybank.elasticservice.model.CustomerDTO
import com.pennybank.elasticservice.model.LoanApplicationMessageDTO
import com.pennybank.elasticservice.repository.LoanApplicationRepository
import com.pennybank.elasticservice.model.LoanApplicationDTO
import spock.lang.Specification

class LoanApplicationMessageProcessorUnitTest extends Specification {
    private CustomerApi customerApi = Mock(CustomerApi)
    private LoanApplicationApi loanApplicationApi = Mock(LoanApplicationApi)
    private LoanApplicationRepository loanApplicationRepository = Mock(LoanApplicationRepository)

    private LoanApplicationMessageProcessor loanApplicationMessageProcessor

    void setup() {
        loanApplicationMessageProcessor = new LoanApplicationMessageProcessor(customerApi,
                loanApplicationApi,
                loanApplicationRepository)
    }

    def "process should save loan entity in elastic search"() {
        given:
        def loanId = 10L
        def customerId = 20L
        def loanApplicationMessageDTO = new LoanApplicationMessageDTO(loanId, customerId)
        when:
        loanApplicationMessageProcessor.process(loanApplicationMessageDTO)
        then:
        1 * loanApplicationRepository.save(_ as LoanApplicationEntity)
        1 * customerApi.get(customerId) >> new CustomerDTO()
        1 * loanApplicationApi.get(loanId) >> new LoanApplicationDTO()
    }
}
