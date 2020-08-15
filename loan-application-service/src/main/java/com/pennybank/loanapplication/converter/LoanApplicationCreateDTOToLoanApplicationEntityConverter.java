package com.pennybank.loanapplication.converter;

import com.pennybank.loanapplication.entity.LoanApplicationEntity;
import com.pennybank.loanapplication.entity.Status;
import com.pennybank.loanapplication.model.LoanApplicationCreateDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanApplicationCreateDTOToLoanApplicationEntityConverter implements Converter<LoanApplicationCreateDTO, LoanApplicationEntity> {
    @Override
    public LoanApplicationEntity convert(LoanApplicationCreateDTO source) {
        LoanApplicationEntity entity = new LoanApplicationEntity();
        BeanUtils.copyProperties(source, entity);
        entity.setStatus(Status.CREATED);
        return entity;
    }
}
