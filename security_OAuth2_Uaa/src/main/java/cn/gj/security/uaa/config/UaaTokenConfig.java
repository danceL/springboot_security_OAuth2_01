package cn.gj.security.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 09:48
 * @version: 1.0
 **/
@Configuration
public class UaaTokenConfig {
    /** 令牌存储策略*/
    @Bean
    public TokenStore tokenStore(){
        /**用内存inMemory方式，生成普通令牌*/
        return new InMemoryTokenStore();
    }
}
