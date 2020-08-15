package com.pennybank.customer.controller;

import com.pennybank.customer.model.CustomerDTO;
import com.pennybank.customer.service.CustomerService;
import com.pennybank.customer.model.CustomerCreateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Api(value = "Panny Bank customer REST API")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Customer with give Id", response = CustomerDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer with given id"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public CustomerDTO getCustomer(@PathVariable("id") Long id) {
        return customerService.getCustomer(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create customer", response = CustomerDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created customer")
    })
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerCreateDTO payload) {
        return customerService.create(payload);
    }
}
