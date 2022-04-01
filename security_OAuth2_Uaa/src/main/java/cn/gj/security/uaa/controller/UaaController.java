package cn.gj.security.uaa.controller;

import cn.gj.security.uaa.service.SpringDataUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author:
 * @date: 2022-03-30 17:28
 * @version: 1.0
 **/
@RestController
public class UaaController {

    @Autowired
    private SpringDataUserDetailsService springDataUserDetailsService;

    @GetMapping(value = "/to")
    public void toIndex(){
        springDataUserDetailsService.loadUserByUsername("zhangsan");
    }
}
