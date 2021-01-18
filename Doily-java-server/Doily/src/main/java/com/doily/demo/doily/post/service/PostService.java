package com.doily.demo.doily.post.service;

import com.doily.demo.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName postService
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:05
 */
@Service
@Transactional(readOnly = false)
public class PostService {
    @Resource
    private PostMapper postMapper;
}
