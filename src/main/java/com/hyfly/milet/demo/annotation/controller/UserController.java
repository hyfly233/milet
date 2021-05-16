package com.hyfly.milet.demo.annotation.controller;

import com.hyfly.milet.demo.pojo.User;
import com.hyfly.milet.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/userA")
@Slf4j
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello milet");
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable("id") String id) {
        return repository.findById(id).map(user -> new ResponseEntity<>(user, HttpStatus.OK)).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public Flux<User> getAll() {
        return repository.findAll();
    }

    /**
     * @return SSE
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return repository.findAll();
    }

    @PostMapping("/")
    public Mono<User> createUser(@RequestBody User user) {

        user.setId(null);
        return repository.save(user);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return repository
                .findById(id)
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return repository.save(u);
                })
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        return repository
                .findById(id)
                .flatMap(user -> repository.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
