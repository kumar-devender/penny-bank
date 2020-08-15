package com.pennybank.loanapplication.util

import com.github.tomakehurst.wiremock.junit.WireMockRule

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

class StubUtil {

    static stubGetCustomer(WireMockRule service, String url, String response) {
        service.stubFor(
                get(urlEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(OK.value())
                                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                        .withBody(response)))
    }
}
