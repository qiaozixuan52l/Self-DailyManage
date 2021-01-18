package com.doily.demo.doily.center.controller;

import com.doily.demo.doily.center.service.CenterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName centerController
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:04
 */
@RestController
@RequestMapping("/center")
public class CenterController {
    @Resource
    private CenterService centerService;
}
