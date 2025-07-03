package com.esunbank.socialmediaapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前端路由控制器
 * 用於處理所有非API請求，將其轉發到index.html
 * 這樣可以確保Vue.js的前端路由能夠正常工作
 */
@Controller
public class FrontendController {

    /**
     * 處理所有非API請求，將其轉發到index.html
     * 這樣可以確保Vue.js的前端路由能夠正常工作
     * 注意：API請求（/api/**）由相應的REST控制器處理
     *
     * @return 視圖名稱
     */
    @GetMapping(value = {"/", "/login", "/register", "/profile", "/home"})
    public String forward() {
        return "forward:/index.html";
    }
}