package cn.gj.security.uaa.dao;

import cn.gj.security.uaa.entity.PermissionEntity;
import cn.gj.security.uaa.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 16:09
 * @version: 1.0
 **/
@Repository
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    /** 根据账号查询用户信息 */
    public UserEntity getUserByUsername(String username){
        String sql = "select id,username,password,fullname,mobile from t_user where username = ?";
        List<UserEntity> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserEntity.class));
        logger.info("[getUserByUsername(String username)][list.toString()]:::" + list.toString());
        if (list != null && list.size() == 1) {
            logger.info("[getUserByUsername(String username)][list.get(0)]:::" + list.get(0));
            return list.get(0);
        }
        return null;
    }

    /** 根据用户id查询用户权限 */
    public List<String> findPermissionsByUserId(String userId){
        logger.info("[findPermissionsByUserId(String userId)][userId]:::" + userId);

        String sql = "SELECT tp.* FROM t_permission tp " +
                "RIGHT JOIN t_role_permission trp ON tp.id = trp.permission_id " +
                "RIGHT JOIN t_user_role tur ON trp.role_id = tur.role_id " +
                "WHERE tur.user_id = ?";
        List<PermissionEntity> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionEntity.class));
        List<String> permissions = new ArrayList<>();
        list.forEach(c -> permissions.add(c.getCode()));
        return permissions;
    }
}
