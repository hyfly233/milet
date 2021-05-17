package com.hyfly.milet.demo.webClient.controller;

import com.hyfly.milet.demo.webClient.api.IUserApi;
import com.hyfly.milet.demo.webClient.pojo.WUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/wc")
public class TestController {

    @Autowired
    IUserApi userApi;

    @GetMapping("/all")
    void getAllUser() {
        Flux<WUser> users = userApi.getAllUser();
        users.subscribe(System.out::println);
    }

    @GetMapping("/{id}")
    void getUserById(@PathVariable("id") String id) {
        userApi.getUserById(id);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id) {
        userApi.deleteUser(id);
    }

    @PostMapping("/")
    void createUser(@RequestBody Mono<WUser> user) {
        userApi.createUser(user);
    }
}
