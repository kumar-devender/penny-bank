package com.pennybank.elasticservice.service;

import com.pennybank.elasticservice.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryBuilder {

    private final JsonHelper jsonHelper;

    public String buildQuery(String templateFile, Map<String, Object> params) {
        log.debug("Building query {} with params {}", templateFile, params);

        VelocityContext context = new VelocityContext();
        params.forEach(context::put);
        context.put(Integer.class.getSimpleName(), Integer.class);
        context.put(Math.class.getSimpleName(), Math.class);

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();

        StringWriter writer = new StringWriter();
        ve.mergeTemplate(templateFile, UTF_8.name(), context, writer);

        String uglyQuery = writer.toString();
        String prettyQuery = jsonHelper.prettify(uglyQuery);
        log.info("Prettified query:\n{}", prettyQuery);
        return uglyQuery;
    }
}
