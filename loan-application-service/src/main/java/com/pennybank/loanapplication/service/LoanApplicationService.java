package com.pennybank.loanapplication.service;

import com.pennybank.loanapplication.converter.ConverterService;
import com.pennybank.loanapplication.entity.LoanApplicationEntity;
import com.pennybank.loanapplication.errors.NotFoundException;
import com.pennybank.loanapplication.publisher.LoanApplicationMessagePublisher;
import com.pennybank.loanapplication.repository.LoanApplicationRepository;
import com.pennybank.loanapplication.client.CustomerService;
import com.pennybank.loanapplication.model.CustomerDTO;
import com.pennybank.loanapplication.model.LoanApplicationCreateDTO;
import com.pennybank.loanapplication.model.LoanApplicationDTO;
import com.pennybank.loanapplication.model.LoanApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanApplicationService {
    private final ConverterService converterService;
    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerService customerService;
    private final LoanApplicationMessagePublisher messagePublisher;

    public LoanApplicationDTO save(LoanApplicationCreateDTO loanApplication) {
        log.info("Creating loan application for customer id[{}]", loanApplication.getCustomerId());
        Long customerId = loanApplication.getCustomerId();
        getCustomerOrThrowException(customerId);
        LoanApplicationEntity entity = converterService.convert(loanApplication, LoanApplicationEntity.class);
        loanApplicationRepository.save(entity);
        messagePublisher.publishMessage(entity);
        return converterService.convert(entity, LoanApplicationDTO.class);
    }

    public LoanApplicationDTO getById(Long id) {
        log.info("Get loan application id[{}]", id);
        return loanApplicationRepository.findById(id)
                .map(entity -> converterService.convert(entity, LoanApplicationDTO.class))
                .orElseThrow(() -> new NotFoundException(format("Could not find loan application with id %s", id)));
    }

    public LoanApplicationResponseDTO getByCustomerId(Long customerId) {
        log.info("Searching loan application for customer id[{}]", customerId);
        CustomerDTO customerDTO = getCustomerOrThrowException(customerId);
        List<LoanApplicationDTO> loanApplications = loanApplicationRepository.findByCustomerId(customerId)
                .stream()
                .map(entity -> converterService.convert(entity, LoanApplicationDTO.class))
                .collect(Collectors.toList());
        return LoanApplicationResponseDTO.builder()
                .loans(loanApplications)
                .customer(customerDTO)
                .build();
    }

    private CustomerDTO getCustomerOrThrowException(Long customerId) {
        return customerService.getCustomerById(customerId)
                .orElseThrow(() -> new NotFoundException(format("Could not find customer with id %s", customerId)));
    }

}
