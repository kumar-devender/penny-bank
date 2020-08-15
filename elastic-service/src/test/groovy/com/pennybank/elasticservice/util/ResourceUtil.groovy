package com.pennybank.elasticservice.util


import org.apache.commons.io.IOUtils

class ResourceUtil {
    static String readResource(String name) {
        return IOUtils.toString(ClassLoader.getSystemResourceAsStream(name))
    }
}
