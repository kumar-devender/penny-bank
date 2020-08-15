package com.pennybank.elasticservice.controller

import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.pennybank.elasticservice.Application
import com.pennybank.elasticservice.model.SearchRequestDTO
import com.pennybank.elasticservice.util.IntegrationTestSpecification
import com.pennybank.elasticservice.util.ResourceUtil
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import static com.pennybank.elasticservice.util.StubUtil.stubElasticSearch
import static java.lang.String.format
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = "integration-test")
class SearchControllerIntegrationTest extends IntegrationTestSpecification {
    private static final RESOURCE_BASE_PATH = '/api/search'
    private static final CATALOGUE = 'loanapplication'
    private static final TEMPLATE_NAME = 'findLoanApplications'
    private static final INDEX = 'loan_application'
    private static final REQUEST_TYPE = '_search'

    @Rule
    public WireMockRule elasticSearchApi = new WireMockRule(WireMockConfiguration.wireMockConfig().port(9292))

    def setup() {
        elasticSearchApi.start()
        def elasticQuery = ResourceUtil.readResource('json/search-query.json')
        def elasticResponse = ResourceUtil.readResource('json/elastic-search-response.json')
        stubElasticSearch(elasticSearchApi, format('/%s/%s', INDEX, REQUEST_TYPE), elasticQuery, elasticResponse)
    }

    def cleanup() {
        elasticSearchApi.stop()
    }

    def 'get loan application with requested criteria using post should be successful'() {
        //@formatter:off
        given:
        def request = buildSearchRequest('json/search-request-meta.json')
        when:
        def response = this.getEndpoint()
            .headers(buildHeaders())
            .body(request).log().all()
            .post(format(RESOURCE_BASE_PATH+'/%s/%s',CATALOGUE, TEMPLATE_NAME))
            .prettyPeek()
        then:
        response.then()
            .statusCode(OK.value())
        //@formatter:on
        elasticSearchApi.verify(1, postRequestedFor(urlPathEqualTo('/loan_application/_search')))
    }

    def buildSearchRequest(String path) {
        String content = ResourceUtil.readResource(path)
        return new SearchRequestDTO(content)
    }
}
