package com.pennybank.customer.controller

import com.pennybank.customer.CustomerApplication
import com.pennybank.customer.repositoty.CustomerRepository
import com.pennybank.customer.model.CustomerCreateDTO
import io.restassured.http.Header
import io.restassured.http.Headers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

import static org.hamcrest.CoreMatchers.equalTo
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@ContextConfiguration(classes = CustomerApplication.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@SqlGroup([
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = '/sql/insert-test-data.sql'),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = '/sql/remove-test-data.sql')
])
@ActiveProfiles(profiles = "integration-test")
class CustomerControllerIntegrationTest extends IntegrationTestSpecification {
    private static final RESOURCE_BASE_PATH = 'api/customers'

    @Autowired
    private CustomerRepository customerRepository

    def "get customer by id should be successful"() {
        //@formatter:off
        given:
        def customerId = customerRepository.getByUserId('12').get().getId()
        when:
        def response = this.getEndpoint()
            .headers(buildHeaders())
            .get(RESOURCE_BASE_PATH+'/'+customerId)
            .prettyPeek()
        then:
        response.then()
            .statusCode(OK.value())
            .body('id', equalTo(customerId.intValue()))
            .body('userId', equalTo('12'))
            .body('phone',equalTo('+49-6893131'))
            .body('email', equalTo('myemail@gmail.com'))
            .body('firstName', equalTo('devender'))
            .body('lastName', equalTo('kumar'))
        //@formatter:on
    }

    def "create customer should be successful"() {
        //@formatter:off
        given:
        def customerCreateDTO = buildCustomerCreateDTO()
        when:
        def response = this.getEndpoint()
            .headers(buildHeaders())
            .body(customerCreateDTO)
            .post(RESOURCE_BASE_PATH)
            .prettyPeek()
        then:
        response.then()
            .statusCode(CREATED.value())
            .body('userId', equalTo('13'))
            .body('phone',equalTo('+49-1233322'))
            .body('email', equalTo('testemail@gmail.com'))
            .body('firstName', equalTo('firstName'))
            .body('lastName', equalTo('lastName'))
        //@formatter:on
    }

    def buildHeaders() {
        new Headers(Arrays.asList(new Header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)))
    }

    def buildCustomerCreateDTO() {
        return CustomerCreateDTO.builder()
                .phone('+49-1233322')
                .email('testemail@gmail.com')
                .firstName('firstName')
                .lastName('lastName')
                .userId('13')
                .build()
    }

}
