package com.pennybank.customer.converter;

import com.pennybank.customer.entity.CustomerEntity;
import com.pennybank.customer.model.CustomerCreateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreateDTOToCustomerEntityConverter implements Converter<CustomerCreateDTO, CustomerEntity> {
    @Override
    public CustomerEntity convert(CustomerCreateDTO source) {
        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(source, entity);
        return entity;
    }
}
