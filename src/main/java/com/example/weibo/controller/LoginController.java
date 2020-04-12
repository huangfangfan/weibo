package com.example.weibo.controller;

/**
 * @program: wespirder-charterone
 * @description:
 * @author: huang fangfan
 * @create: 2020-03-11 16:56
 */

import com.example.weibo.pojo.User;
import com.example.weibo.reslut.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@Controller
public class LoginController {

    /*@CrossOrigin
    @RequestMapping(value = "api/login",method = RequestMethod.POST)
    @ResponseBody*/
   /* public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String tel = requestUser.getTel();
        tel = HtmlUtils.htmlEscape(tel);
        System.out.println("执行");
        if (!Objects.equals("123456", tel) || !Objects.equals("123456", requestUser.getPass())) {
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        } else {
            System.out.println(requestUser);
            return new Result(200);
        }
    }*/
}