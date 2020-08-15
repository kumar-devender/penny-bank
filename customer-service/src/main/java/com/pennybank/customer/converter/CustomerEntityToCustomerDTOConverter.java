package com.pennybank.customer.converter;

import com.pennybank.customer.entity.CustomerEntity;
import com.pennybank.customer.model.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityToCustomerDTOConverter implements Converter<CustomerEntity, CustomerDTO> {
    @Override
    public CustomerDTO convert(CustomerEntity source) {
        CustomerDTO target = new CustomerDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
