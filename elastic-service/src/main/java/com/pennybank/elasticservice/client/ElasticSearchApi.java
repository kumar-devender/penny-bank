package com.pennybank.elasticservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:9200", name = "search")
public interface ElasticSearchApi {
    @RequestMapping(method = RequestMethod.POST,
            value = "/{index}/{request_type}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    String search(@PathVariable("index") String index,
                  @PathVariable("request_type") String requestType,
                  @RequestBody final String query);
}
