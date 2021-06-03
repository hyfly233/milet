package com.hyfly.milet.demo.demo_annotation.controller;

import com.hyfly.milet.demo.demo_common.pojo.User;
import com.hyfly.milet.demo.demo_common.repository.UserRepository;
import com.hyfly.milet.demo.demo_common.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


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

    @GetMapping("/age/{start}/{end}")
    public Flux<User> getUserByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return repository.findByAgeBetween(start, end);
    }

    @GetMapping("/old")
    public Flux<User> oldUser() {
        return repository.oldUser();
    }

    @GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetUserByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return repository.findByAgeBetween(start, end);
    }

    @PostMapping("/")
    public Mono<User> createUser(@Valid @RequestBody User user) {
        user.setId(null);
        CheckUtil.checkName(user.getName());
        return repository.save(user);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @Valid @RequestBody User user) {
        CheckUtil.checkName(user.getName());
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
