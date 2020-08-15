package com.pennybank.loanapplication.controller

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.pennybank.loanapplication.LoanApplication
import com.pennybank.loanapplication.model.CustomerDTO
import com.pennybank.loanapplication.model.LoanApplicationCreateDTO
import com.pennybank.loanapplication.repository.LoanApplicationRepository
import com.pennybank.loanapplication.util.JsonHelper
import com.pennybank.loanapplication.util.IntegrationTestSpecification
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.pennybank.loanapplication.util.StubUtil.stubGetCustomer
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration(classes = LoanApplication.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@SqlGroup([
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = '/sql/insert-test-data.sql'),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = '/sql/remove-test-data.sql')
])
@ActiveProfiles(profiles = "integration-test")
class LoanApplicationControllerIntegrationTest extends IntegrationTestSpecification {
    private static final RESOURCE_BASE_PATH = '/api/loanapplications'
    @Autowired
    private JsonHelper jsonHelper
    @Autowired
    private LoanApplicationRepository loanApplicationRepository

    @Rule
    public WireMockRule customerApi = new WireMockRule()

    def setup() {
        customerApi.start()
    }

    def cleanup() {
        customerApi.stop()
    }

    def 'create new application should be successful'() {
        //@formatter:off
        given:
        def customerId = 12
        stubGetCustomer(customerApi, '/api/customers/12', jsonHelper.toJson(buildCustomer(12)))
        when:
        def response = this.getEndpoint()
            .headers(buildHeaders())
            .body(buildLoanApplication())
            .post(RESOURCE_BASE_PATH)
            .prettyPeek()
        then:
        response.then()
            .statusCode(CREATED.value())
            .body('customerId',CoreMatchers.equalTo(customerId))
            .body('duration',CoreMatchers.is(12))
            .body('status',CoreMatchers.equalTo('CREATED'))
            .body('amount',CoreMatchers.is(1000))
        //@formatter:on
        customerApi.verify(1, getRequestedFor(urlPathEqualTo('/api/customers/12')))
    }

    def 'get loan applications with customer id should be successful'() {
        //@formatter:off
        given:
        def customerId = 11
        stubGetCustomer(customerApi, '/api/customers/11', jsonHelper.toJson(buildCustomer(11)))
        when:
        def response = this.getEndpoint()
            .headers(buildHeaders())
        .param("customerId", customerId)
            .get(RESOURCE_BASE_PATH)
            .prettyPeek()
        then:
        response.then()
            .statusCode(OK.value())
            .body('customer.firstName', CoreMatchers.is('devender'))
            .body('customer.lastName', CoreMatchers.is('kumar'))
            .body('customer.id', CoreMatchers.is(customerId))
            .body('loans[0].customerId',CoreMatchers.equalTo(customerId))
            .body('loans[0].duration',CoreMatchers.is(24))
            .body('loans[0].status',CoreMatchers.equalTo('CREATED'))
            .body('loans[0].amount',CoreMatchers.is(50000))
        //@formatter:on
        customerApi.verify(1, getRequestedFor(urlPathEqualTo('/api/customers/11')))
    }

    def 'get loan applications should be successful'() {
        //@formatter:off
        given:
        def customerId = 11
        def loanApplicationId = loanApplicationRepository.findByCustomerId(customerId).get(0).getId()
        stubGetCustomer(customerApi, '/api/customers/'+customerId, jsonHelper.toJson(buildCustomer(customerId)))
        when:
        def response = this.getEndpoint()
            .headers(buildHeaders())
            .get(RESOURCE_BASE_PATH + '/'+loanApplicationId)
            .prettyPeek()
        then:
        response.then()
            .statusCode(OK.value())
            .body('customerId',CoreMatchers.equalTo(customerId))
            .body('duration',CoreMatchers.is(24))
            .body('status',CoreMatchers.equalTo('CREATED'))
            .body('amount',CoreMatchers.is(50000))
        //@formatter:on
        customerApi.verify(0, anyRequestedFor(anyUrl()))
    }

    def buildLoanApplication() {
        return LoanApplicationCreateDTO.builder()
                .customerId(12L)
                .amount(new BigDecimal(1000))
                .duration(12)
                .build()
    }

    def buildCustomer(Long id) {
        return CustomerDTO.builder()
                .firstName('devender')
                .lastName('kumar')
                .id(id)
                .build()
    }

}
