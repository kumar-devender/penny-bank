package com.pennybank.elasticservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.math.BigDecimal;

@Document(indexName = LoanApplicationEntity.INDEX, type = LoanApplicationEntity.TYPE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationEntity {
    public static final String INDEX = "loan_application";
    public static final String TYPE = "loanapplication";
    @Id
    private Long id;
    private String userId;
    private Integer duration;
    private BigDecimal amount;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @Mapping(mappingPath = "/mapping/keytext-field.json")
    private Status status;
}
