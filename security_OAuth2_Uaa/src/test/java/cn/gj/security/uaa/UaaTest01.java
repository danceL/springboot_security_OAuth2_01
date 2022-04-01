package cn.gj.security.uaa;

import cn.gj.security.uaa.config.UaaTokenConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 09:49
 * @version: 1.0
 **/
@RunWith(SpringRunner.class)
public class UaaTest01 {

    @Test
    public void test01(){
        UaaTokenConfig token = new UaaTokenConfig();
        TokenStore tokenStore = token.tokenStore();
        System.out.println(tokenStore.toString());
    }
}
