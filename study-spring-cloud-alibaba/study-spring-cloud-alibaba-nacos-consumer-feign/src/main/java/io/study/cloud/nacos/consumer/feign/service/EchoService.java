package io.study.cloud.nacos.consumer.feign.service;

import io.study.cloud.nacos.consumer.feign.service.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-provider", fallback = EchoServiceFallback.class)
public interface EchoService {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}