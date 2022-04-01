package cn.gj.security.uaa.entity;

import lombok.Data;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 16:08
 * @version: 1.0
 **/
@Data
public class UserEntity {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
