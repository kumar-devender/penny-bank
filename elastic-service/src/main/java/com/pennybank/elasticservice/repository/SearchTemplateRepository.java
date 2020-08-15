package com.pennybank.elasticservice.repository;

import com.pennybank.elasticservice.model.QueryId;
import com.pennybank.elasticservice.model.QueryTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@Slf4j
public class SearchTemplateRepository {
    private static final Yaml YAML = new Yaml();

    private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private final Map<QueryId, QueryTemplate> templatesByName = new HashMap<>();

    @Value("${pannybank.search.queryFilesLocation}")
    private String queryFilesLocation;

    @PostConstruct
    public void init() throws IOException {
        for (Resource queryResource : resolver.getResources(queryFilesLocation)) {
            log.info("Reading query file {}", queryResource.getFilename());

            QueryTemplate queryTemplate = YAML.loadAs(queryResource.getInputStream(), QueryTemplate.class);

            templatesByName.put(
                    new QueryId(
                            queryTemplate.getCatalogue(),
                            queryTemplate.getName()
                    ),
                    queryTemplate);
        }
    }

    public Optional<QueryTemplate> getById(QueryId queryId) {
        return ofNullable(templatesByName.get(queryId));
    }
}
