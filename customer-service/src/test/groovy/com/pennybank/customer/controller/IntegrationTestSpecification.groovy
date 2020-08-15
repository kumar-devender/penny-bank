package com.pennybank.customer.controller

import groovy.transform.CompileStatic
import io.restassured.specification.RequestSpecification
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

import static io.restassured.RestAssured.given

class IntegrationTestSpecification extends Specification {
    @Value('http://localhost')
    protected String host

    @Value('${local.server.port}')
    protected int port

    @CompileStatic
    RequestSpecification getEndpoint() {
        given()
                .baseUri(host)
                .port(port)
    }
}
