package com.hyfly.milet.demo.webClient.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodInfo {

    private String url;

    private HttpMethod method;

    private Map<String, Object> arg;

    private Mono<?> body;

    private boolean isReturnFlux;

    private Class<?> returnElementType;
}
