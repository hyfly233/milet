package com.hyfly.milet.demo.routerFunction.handlers;

import com.hyfly.milet.demo.exception.UserCheckedException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-99)
public class ExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        ServerHttpResponse response = serverWebExchange.getResponse();

        // 设置响应头 400
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        String errMsg = exToString(throwable);

        DataBuffer buffer = response.bufferFactory().wrap(errMsg.getBytes());

        return response.writeWith(Mono.just(buffer));
    }

    String exToString(Throwable ex) {

        if (ex instanceof UserCheckedException) {
            // 已知异常
            UserCheckedException e = (UserCheckedException) ex;
            return e.toString();
        } else {
            // 未知异常 打印堆栈 方便定位
            ex.printStackTrace();
            return ex.toString();
        }
    }
}
