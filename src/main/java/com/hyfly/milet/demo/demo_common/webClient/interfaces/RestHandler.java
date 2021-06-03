package com.hyfly.milet.demo.demo_common.webClient.interfaces;

import com.hyfly.milet.demo.demo_common.webClient.bean.MethodInfo;
import com.hyfly.milet.demo.demo_common.webClient.bean.ServerInfo;

public interface RestHandler {

    void init(ServerInfo serverInfo);

    Object invokeRest(MethodInfo methodInfo);
}
