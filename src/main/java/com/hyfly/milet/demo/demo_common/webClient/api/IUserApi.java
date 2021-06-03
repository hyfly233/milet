package com.hyfly.milet.demo.demo_common.webClient.api;

import com.hyfly.milet.demo.demo_common.webClient.annotation.ApiServer;
import com.hyfly.milet.demo.demo_common.webClient.pojo.WUser;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost:8080/userA")
public interface IUserApi {

    @GetMapping("/all")
    Flux<WUser> getAllUser();

    @GetMapping("/{id}")
    Mono<WUser> getUserById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    Mono<Void> deleteUser(@PathVariable("id") String id);

    @PostMapping("/")
    Mono<WUser> createUser(@RequestBody Mono<WUser> user);
}
