package com.doily.demo.doily.club.controller;

import com.doily.demo.doily.club.service.ClubService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName clubController
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:05
 */
@RestController
@RequestMapping("/club")
public class ClubController {
    @Resource
    private ClubService clubService;
}
