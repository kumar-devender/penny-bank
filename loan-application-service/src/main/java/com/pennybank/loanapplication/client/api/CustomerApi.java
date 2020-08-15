package com.pennybank.loanapplication.client.api;

import com.pennybank.loanapplication.model.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("customer-service")
public interface CustomerApi {
    @RequestMapping(method = RequestMethod.GET,
            value = "/api/customers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerDTO get(@PathVariable("id") Long id);
}
