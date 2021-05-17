package com.hyfly.milet;

import com.hyfly.milet.demo.webClient.api.IUserApi;
import com.hyfly.milet.demo.webClient.interfaces.ProxyCreator;
import com.hyfly.milet.demo.webClient.interfaces.impl.JdkProxyCreatorImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MiletApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiletApplication.class, args);
    }

    @Bean
    ProxyCreator jdkProxyCreator() {
        return new JdkProxyCreatorImpl();
    }

    @Bean
    FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator) {
        return new FactoryBean<>() {
            @Override
            public IUserApi getObject() {
                return (IUserApi) proxyCreator.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }
}
