package com.hyfly.milet.demo.webClient.interfaces;

import com.hyfly.milet.demo.webClient.bean.MethodInfo;
import com.hyfly.milet.demo.webClient.bean.ServerInfo;

public interface RestHandler {

    void init(ServerInfo serverInfo);

    Object invokeRest(MethodInfo methodInfo);
}
