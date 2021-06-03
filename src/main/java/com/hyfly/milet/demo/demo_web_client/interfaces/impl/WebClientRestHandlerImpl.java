package com.hyfly.milet.demo.demo_web_client.interfaces.impl;

import com.hyfly.milet.demo.demo_web_client.bean.MethodInfo;
import com.hyfly.milet.demo.demo_web_client.bean.ServerInfo;
import com.hyfly.milet.demo.demo_web_client.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientRestHandlerImpl implements RestHandler {

    WebClient client;

    @Override
    public void init(ServerInfo serverInfo) {
        this.client = WebClient.create(serverInfo.getUrl());
    }

    @Override
    public Object invokeRest(MethodInfo methodInfo) {

        Object result = null;

        WebClient.ResponseSpec retrieve = this.client.method(methodInfo.getMethod()).uri(methodInfo.getUrl()).accept(MediaType.APPLICATION_JSON).retrieve();

        if (methodInfo.isReturnFlux()) {
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }

        return result;
    }
}
