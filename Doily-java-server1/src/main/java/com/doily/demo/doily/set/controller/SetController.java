package com.doily.demo.doily.set.controller;

import com.doily.demo.doily.post.service.PostService;
import com.doily.demo.doily.set.service.SetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName setController
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:05
 */
@RestController
@RequestMapping("/set")
public class SetController {
    @Resource
    private SetService setService;

}
