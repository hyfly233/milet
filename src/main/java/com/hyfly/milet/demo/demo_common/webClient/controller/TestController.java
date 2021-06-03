package com.hyfly.milet.demo.demo_common.webClient.controller;

import com.hyfly.milet.demo.demo_common.webClient.api.IUserApi;
import com.hyfly.milet.demo.demo_common.webClient.pojo.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TestController {

    @Autowired
    IUserApi userApi;

    @GetMapping("/wc")
    void test() {
        Flux<WUser> users = userApi.getAllUser();
        users.subscribe(System.out::println);
    }
}