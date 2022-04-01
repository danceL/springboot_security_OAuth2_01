package cn.gj.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务配置：
 * 1、客户端详情服务
 * 2、令牌管理服务
 * 3、令牌访问端点配置
 * 4、令牌端点的安全约束
 * 5、授权码服务
 **/
@Configuration
@EnableAuthorizationServer
public class UaaAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    /** 令牌存储策略 */
    @Autowired
    private TokenStore tokenStore;
    /** 客户端详情服务 */
    @Autowired
    private ClientDetailsService clientDetailsService;
    /** 授权码 */
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /** 认证管理器 */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**配置客户端详细信息服务*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //super.configure(clients);
        /**
         * .inMemory()：使用inMemory存储
         * .withClient：client_id
         * .secret(new BCryptPasswordEncoder().encode("secret"))：客户端密钥
         * .resourceIds("res1")：资源列表
         * .authorizedGrantTypes("authorization_code", "password")：
         *   该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
         * .scopes("all")：允许的授权范围
         * .autoApprove(false)：false跳转到授权页面；true不跳转，直接发令牌
         * .redirectUris("http://www.baidu.com")：验证回调地址
         * */
        clients.inMemory()
                .withClient("c1")
                .secret(new BCryptPasswordEncoder().encode("secret"))
                .resourceIds("res1")
                .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")
                .scopes("all")
                .autoApprove(false)
                .redirectUris("http://www.baidu.com");
    }

    /** 令牌访问端点配置
     * -----------------------------------
     * authenticationManager：认证管理器
     * authorizationCodeServices：授权码服务
     * tokenServices：令牌管理服务
     * */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //super.configure(endpoints);
        endpoints.authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices)
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /** 配置令牌端点的安全约束 */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //super.configure(security);
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 令牌管理服务
     * ----------------------------------------
     * setClientDetailsService：客户端详情服务
     * setSupportRefreshToken：支持刷新令牌
     * setTokenStore：支持存储策略
     * setAccessTokenValiditySeconds：令牌默认有效期2小时
     * setRefreshTokenValiditySeconds：刷新令牌默认有效期3天
     * */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();

        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        services.setAccessTokenValiditySeconds(7200);
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }

    /** 设置授权码模式，授权码如何存取 */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }

}
