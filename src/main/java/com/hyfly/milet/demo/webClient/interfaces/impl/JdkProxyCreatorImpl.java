package com.hyfly.milet.demo.webClient.interfaces.impl;

import com.hyfly.milet.demo.webClient.annotation.ApiServer;
import com.hyfly.milet.demo.webClient.bean.MethodInfo;
import com.hyfly.milet.demo.webClient.bean.ServerInfo;
import com.hyfly.milet.demo.webClient.interfaces.ProxyCreator;
import com.hyfly.milet.demo.webClient.interfaces.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class JdkProxyCreatorImpl implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {

        log.info("JdkProxyCreatorImpl createProxy type:" + type);
        ServerInfo serverInfo = extractServerInfo(type);

        log.info("JdkProxyCreatorImpl createProxy serverInfo:" + serverInfo);

        RestHandler handler = new WebClientRestHandlerImpl();

        handler.init(serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodInfo methodInfo = extractMethodInfo(method, args);

                Object ret = handler.invokeRest(methodInfo);
                return null;
            }
        });
    }

    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();

        Annotation[] methodAnnotations = method.getAnnotations();

        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof GetMapping) {
                GetMapping a = (GetMapping) annotation;

                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);

            } else if (annotation instanceof PostMapping) {
                PostMapping a = (PostMapping) annotation;

                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);

            } else if (annotation instanceof PutMapping) {
                PutMapping a = (PutMapping) annotation;

                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);

            } else if (annotation instanceof DeleteMapping) {

                DeleteMapping a = (DeleteMapping) annotation;

                methodInfo.setUrl(a.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }
        }

        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {

            Map<String, Object> params = new LinkedHashMap<>();
            methodInfo.setArg(params);

            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);

            if (pathVariable != null) {
                params.put(pathVariable.value(), args[i]);
            }

            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);

            if (requestBody != null) {
                methodInfo.setBody((Mono<?>) args[i]);
            }
        }

        extractReturnInfo(method, methodInfo);

        return methodInfo;
    }

    private void extractReturnInfo(Method method, MethodInfo methodInfo) {
        methodInfo.setReturnFlux(method.getReturnType().isAssignableFrom(Flux.class));

        Class<?> elementType = extractElementType(method.getGenericReturnType());

        methodInfo.setReturnElementType(elementType);
    }

    private Class<?> extractElementType(Type genericReturnType) {
        Type[] types = ((ParameterizedType) genericReturnType).getActualTypeArguments();

        return (Class<?>) types[0];
    }


    private ServerInfo extractServerInfo(Class<?> type) {

        ServerInfo serverInfo = new ServerInfo();

        ApiServer apiServer = type.getAnnotation(ApiServer.class);

        serverInfo.setUrl(apiServer.value());

        return serverInfo;
    }
}
