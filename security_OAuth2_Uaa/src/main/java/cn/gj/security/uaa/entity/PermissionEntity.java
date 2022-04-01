package cn.gj.security.uaa.entity;

import lombok.Data;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 16:04
 * @version: 1.0
 **/
@Data
public class PermissionEntity {
    private String id;
    private String code;
    private String description;
    private String url;
}
