package com.doily.demo.doily.club.service;

import com.doily.demo.mapper.ClubMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName clubService
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:05
 */
@Service
@Transactional(readOnly = false)
public class ClubService {
    @Resource
    private ClubMapper clubMapper;
}
