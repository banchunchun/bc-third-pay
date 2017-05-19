package com.bc.third.pay.web.controller;


import com.bc.third.pay.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-11
 * Time:  下午 4:36.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class UserController {

    @Autowired
    private AlipayProperties alipayProperties;

    @RequestMapping(value = "/index.htm")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
        System.out.println(alipayProperties.getPartner());
        System.out.println(alipayProperties.getKey());

        return null;
    }
}
