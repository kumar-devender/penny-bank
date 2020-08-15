package com.pennybank.elasticservice.service;

import com.pennybank.elasticservice.client.ElasticSearchApi;
import com.pennybank.elasticservice.model.QueryTemplate;
import com.pennybank.elasticservice.model.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryExecutor {
    private final ElasticSearchApi searchApi;
    private final QueryBuilder queryBuilder;

    public SearchResponse execute(QueryTemplate queryTemplate, Map<String, Object> params) {
        String query = queryBuilder.buildQuery(queryTemplate.getTemplateFile(), params);
        String response = searchApi.search(queryTemplate.getIndex(), queryTemplate.getType(), query);
        log.debug("Elastic search Response [{}]", response);
        return new SearchResponse(response);
    }
}
