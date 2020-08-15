package com.pennybank.elasticservice.service;

import com.pennybank.elasticservice.errors.NotFoundException;
import com.pennybank.elasticservice.model.QueryId;
import com.pennybank.elasticservice.model.QueryTemplate;
import com.pennybank.elasticservice.model.SearchRequestDTO;
import com.pennybank.elasticservice.model.SearchResponse;
import com.pennybank.elasticservice.repository.SearchTemplateRepository;
import com.pennybank.elasticservice.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class SearchService {
    private final SearchTemplateRepository searchTemplateRepository;
    private final QueryExecutor queryExecutor;
    private final JsonHelper jsonHelper;

    public SearchResponse search(String catalogue, String template, SearchRequestDTO searchRequest) {
        log.info("Received search request template [{}] , catalogue [{}] with meta [{}]", template, catalogue, searchRequest);
        QueryTemplate queryTemplate = searchTemplateRepository.getById(new QueryId(catalogue, template))
                .orElseThrow(() -> new NotFoundException(String.format("No search template found for catalogue %s and name %s", catalogue, template)));
        return queryExecutor.execute(queryTemplate, jsonHelper.toMap(searchRequest.getMeta()));
    }

}
