package com.pennybank.loanapplication.converter;


import com.pennybank.loanapplication.entity.Status;

public class StatusConverter extends AbstractEnumUserType {
    @Override
    public Class returnedClass() {
        return Status.class;
    }
}
