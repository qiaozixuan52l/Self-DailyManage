package com.doily.demo.doily.post.controller;

import com.doily.demo.doily.club.service.ClubService;
import com.doily.demo.doily.post.service.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName postController
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:05
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;
}
