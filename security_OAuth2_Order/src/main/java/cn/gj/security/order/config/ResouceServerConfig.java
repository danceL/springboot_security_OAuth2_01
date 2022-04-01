package cn.gj.security.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @description:
 * @author:
 * @date: 2022-03-31 11:31
 * @version: 1.0
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "res1";

    /** 资源访问配置
     * resourceId：资源ID
     * tokenServices：验证令牌服务
     * stateless：
     * */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //super.configure(resources);
        resources
                .resourceId(RESOURCE_ID)
                .tokenServices(tokenService())
                .stateless(true);
    }

    /** HttpSecurity配置
     * authorizeRequests：
     * antMatchers：匹配哪些路径
     * access：允许授权的范围
     * and().csrf().disable()：关闭csrf
     * sessionManagement配置：不记录session
     * */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /** 资源服务令牌解析服务 */
    @Bean
    public ResourceServerTokenServices tokenService() {
        /** 使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret */
        RemoteTokenServices service=new RemoteTokenServices();
        service.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token");
        service.setClientId("c1");
        service.setClientSecret("secret");
        return service;
    }

}
