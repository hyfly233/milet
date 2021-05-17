package com.hyfly.milet.demo.webClient.interfaces.impl;

import com.hyfly.milet.demo.webClient.annotation.ApiServer;
import com.hyfly.milet.demo.webClient.bean.MethodInfo;
import com.hyfly.milet.demo.webClient.bean.ServerInfo;
import com.hyfly.milet.demo.webClient.interfaces.ProxyCreator;
import com.hyfly.milet.demo.webClient.interfaces.RestHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class JdkProxyCreatorImpl implements ProxyCreator {
    @Override
    public Object createProxy(Class<?> type) {

        log.info("JdkProxyCreatorImpl createProxy type:" + type);
        ServerInfo serverInfo = extractServerInfo(type);

        log.info("JdkProxyCreatorImpl createProxy serverInfo:" + serverInfo);

        RestHandler handler = null;

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
        return null;
    }

    private ServerInfo extractServerInfo(Class<?> type) {

        ServerInfo serverInfo = new ServerInfo();

        ApiServer apiServer = type.getAnnotation(ApiServer.class);

        serverInfo.setUrl(apiServer.value());

        return serverInfo;
    }
}
