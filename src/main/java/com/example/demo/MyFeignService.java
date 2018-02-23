package com.example.demo;

import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.List;

@FeignClient(url = "${service.url}", name = "example")
public interface MyFeignService {
    @Headers("Content-Type: application/json")
    @RequestLine("GET /")
    List<SimplePOJO> simpleGet();
}
