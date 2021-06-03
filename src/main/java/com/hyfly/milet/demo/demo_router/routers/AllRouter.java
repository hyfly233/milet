package com.hyfly.milet.demo.demo_router.routers;

import com.hyfly.milet.demo.demo_router.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AllRouter {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler) {

        return nest(
                // 相当于 RequestMapping("/user")
                path("/userR"),
                // 相当于 GetMapping("/")
                route(GET("/"), handler::getAllUser)
                        // 相当于 PostMapping("/")
                        .andRoute(POST("/").and(accept(APPLICATION_JSON)), handler::createUser)
                        // 相当于 DeleteMapping("/{id}")
                        .andRoute(DELETE("/{id}"), handler::deleteUserById)
        );
    }
}
