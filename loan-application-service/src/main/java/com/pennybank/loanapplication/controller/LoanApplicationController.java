package com.pennybank.loanapplication.controller;

import com.pennybank.loanapplication.model.LoanApplicationCreateDTO;
import com.pennybank.loanapplication.model.LoanApplicationDTO;
import com.pennybank.loanapplication.model.LoanApplicationResponseDTO;
import com.pennybank.loanapplication.service.LoanApplicationService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/loanapplications")
@RequiredArgsConstructor
@Api(value = "Panny Bank Loan Application REST API")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    @GetMapping(
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get loan application with given customer id", response = LoanApplicationResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved loan application with given customer id"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public LoanApplicationResponseDTO getLoans(@RequestParam(value = "customerId") Long customerId) {
        return loanApplicationService.getByCustomerId(customerId);
    }

    @GetMapping(value = "/{id}",
            produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get loan application with give loan application id", response = LoanApplicationDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved loan application with given customer id"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public LoanApplicationDTO getLoanApplication(@PathVariable(value = "id") Long id) {
        return loanApplicationService.getById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create loan application", response = LoanApplicationDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created loan application")
    })
    public LoanApplicationDTO save(@RequestBody @Valid LoanApplicationCreateDTO loanApplication) {
        return loanApplicationService.save(loanApplication);
    }
}
