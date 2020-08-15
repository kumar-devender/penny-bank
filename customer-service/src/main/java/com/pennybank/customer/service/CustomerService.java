package com.pennybank.customer.service;

import com.pennybank.customer.model.CustomerDTO;
import com.pennybank.customer.repositoty.CustomerRepository;
import com.pennybank.customer.converter.ConverterService;
import com.pennybank.customer.entity.CustomerEntity;
import com.pennybank.customer.errors.NotFoundException;
import com.pennybank.customer.model.CustomerCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ConverterService converterService;

    public CustomerDTO getCustomer(Long id) {
        log.info("get customer with id [{}]", id);
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Customer does not exist with id %s", id)));
        return converterService.convert(entity, CustomerDTO.class);
    }

    public CustomerDTO create(CustomerCreateDTO payload) {
        log.info("Creating customer for user [{}]", payload.getUserId());
        CustomerEntity entity = converterService.convert(payload, CustomerEntity.class);
        customerRepository.save(entity);
        return converterService.convert(entity, CustomerDTO.class);
    }
}
