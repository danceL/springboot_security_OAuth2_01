package cn.gj.security.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author:
 * @date: 2022-03-31 11:15
 * @version: 1.0
 **/
@RestController
public class OrderController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1(){
        return "访问资源1";
    }
}
