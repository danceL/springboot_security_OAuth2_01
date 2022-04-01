package cn.gj.security.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 00:13
 * @version: 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"cn.gj.security.uaa"})
public class UaaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(UaaServerApp.class);
    }
}
