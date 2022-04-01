package cn.gj.security.uaa.service;

import cn.gj.security.uaa.dao.UserDao;
import cn.gj.security.uaa.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 16:12
 * @version: 1.0
 **/
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataUserDetailsService.class);

    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.getUserByUsername(username);

        if (userEntity == null) { return null; }

        List<String> permissions = userDao.findPermissionsByUserId(userEntity.getId());
        /** 将permissions转换成数组 */
        String[] permissionArr = new String[permissions.size()];
        permissions.toArray(permissionArr);
        for (int i = 0; i < permissionArr.length; i++) {
            logger.info("[loadUserByUsername(String username)][permissionArr]:::" + permissionArr[i]);
        }

        //UserDetails userDetails = User.withUsername(user.getFullname()).password(user.getPassword()).authorities("p1").build();
        UserDetails userDetails = User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword()).authorities(permissionArr).build();
        return userDetails;
    }
}
