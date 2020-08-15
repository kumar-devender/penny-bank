package com.pennybank.elasticservice.client;

import com.pennybank.elasticservice.model.LoanApplicationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("loan-application-service")
public interface LoanApplicationApi {
    @RequestMapping(method = RequestMethod.GET,
            value = "/api/loanapplications/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    LoanApplicationDTO get(@PathVariable("id") Long id);
}
