package com.pennybank.loanapplication.converter;

import com.pennybank.loanapplication.entity.LoanApplicationEntity;
import com.pennybank.loanapplication.model.LoanApplicationDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanApplicationEntityToLoanApplicationDTOConverter implements Converter<LoanApplicationEntity, LoanApplicationDTO> {
    @Override
    public LoanApplicationDTO convert(LoanApplicationEntity source) {
        LoanApplicationDTO target = new LoanApplicationDTO();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
