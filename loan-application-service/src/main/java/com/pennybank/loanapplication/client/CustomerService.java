package com.pennybank.loanapplication.client;

import com.pennybank.loanapplication.model.CustomerDTO;
import com.pennybank.loanapplication.client.api.CustomerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerApi customerApi;

    public Optional<CustomerDTO> getCustomerById(Long customerId) {
        try {
            log.info("Searching customer with id [{}]", customerId);
            return ofNullable(customerApi.get(customerId));
        } catch (Exception e) {
            log.warn("Exception while finding customer with customer id [{}]", customerId, e);
        }
        return empty();
    }
}
