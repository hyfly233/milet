package com.hyfly.milet.demo.demo_web_client.interfaces;

import com.hyfly.milet.demo.demo_web_client.bean.MethodInfo;
import com.hyfly.milet.demo.demo_web_client.bean.ServerInfo;

public interface RestHandler {

    void init(ServerInfo serverInfo);

    Object invokeRest(MethodInfo methodInfo);
}
