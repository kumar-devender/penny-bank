package com.pennybank.elasticservice.util

import com.github.tomakehurst.wiremock.junit.WireMockRule
import lombok.experimental.UtilityClass
import org.springframework.http.HttpStatus

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@UtilityClass
class StubUtil {
    static stubElasticSearch(WireMockRule service, String url, String payload, String response) {
        service.stubFor(post(urlPathEqualTo(url))
                .withRequestBody(equalToJson(payload, true, true))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(response)
                )
        )

    }
}
