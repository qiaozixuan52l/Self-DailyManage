package com.doily.demo.doily.center.service;
import com.doily.demo.mapper.CenterMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName centerService
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:04
 */
@Service
@Transactional(readOnly = false)
public class CenterService {
    @Resource
    private CenterMapper centerMapper;
}
