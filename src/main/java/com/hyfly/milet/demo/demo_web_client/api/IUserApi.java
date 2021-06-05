package com.hyfly.milet.demo.demo_web_client.api;

import com.hyfly.milet.demo.demo_web_client.annotation.ApiServer;
import com.hyfly.milet.demo.demo_web_client.pojo.WUser;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost:23333/userA")
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
