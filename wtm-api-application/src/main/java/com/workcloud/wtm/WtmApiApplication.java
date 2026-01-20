package com.workcloud.wtm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot Application class.
 * This is the entry point for the WTM API application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.workcloud.wtm.feeds",
    "com.workcloud.wtm.feednotes",
    "com.workcloud.wtm.surveys",
    "com.workcloud.wtm.rtm",
    "com.workcloud.wtm.users",
    "com.workcloud.wtm.sessions",
    "org.openapitools"
})
public class WtmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtmApiApplication.class, args);
    }
}
