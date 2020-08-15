package com.pennybank.elasticservice.repository;

import com.pennybank.elasticservice.document.LoanApplicationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends CrudRepository<LoanApplicationEntity, Long> {
}
