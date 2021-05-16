package com.hyfly.milet.demo.routerFunction.handlers;

import com.hyfly.milet.demo.pojo.User;
import com.hyfly.milet.demo.repository.UserRepository;
import com.hyfly.milet.demo.utils.CheckUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {

    private final UserRepository repository;

    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.repository.findAll(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {

        Mono<User> user = request.bodyToMono(User.class);

        return user.flatMap(u -> {
//            CheckUtil.checkName(u.getName());

            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(this.repository.save(u), User.class);
        });
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest request) {

        String id = request.pathVariable("id");

        return this.repository.findById(id).flatMap(e -> this.repository.delete(e).then(ok().build()).switchIfEmpty(notFound().build()));
    }
}
