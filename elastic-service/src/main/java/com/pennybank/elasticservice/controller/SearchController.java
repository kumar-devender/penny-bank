package com.pennybank.elasticservice.controller;

import com.pennybank.elasticservice.model.SearchRequestDTO;
import com.pennybank.elasticservice.service.SearchService;
import com.pennybank.elasticservice.model.SearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Api(value = "Panny Bank elastic search REST API")
public class SearchController {
    private final SearchService searchService;

    @PostMapping(value = "/{catalogue}/{template}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search loan application with given request meta, catalogue and template", response = SearchResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved loan application with given meta, catalogue and template")
    })
    public SearchResponse search(@PathVariable("catalogue") String catalogue,
                                 @PathVariable("template") String template,
                                 @RequestBody @Valid SearchRequestDTO searchRequest) {
        return searchService.search(catalogue, template, searchRequest);
    }
}
