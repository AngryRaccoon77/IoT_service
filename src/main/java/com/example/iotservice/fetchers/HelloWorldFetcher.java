package com.example.iotservice.fetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class HelloWorldFetcher {

    @DgsQuery
    public String hello() {
        return "Hello, world!";
    }
}