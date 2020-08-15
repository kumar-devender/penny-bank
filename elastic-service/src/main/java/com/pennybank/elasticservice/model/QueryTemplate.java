package com.pennybank.elasticservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryTemplate {
    /**
     * Catalogue to which the template belongs to
     */
    private String catalogue;

    /**
     * Holds the search query name
     */
    private String name;

    /**
     * Holds the index name the query references
     */
    private String index;

    /**
     * Holds the request type (_search / _count / etc)
     */
    private String type;

    /**
     * Template file name
     */
    private String templateFile;

}
