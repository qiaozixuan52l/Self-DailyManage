package com.doily.demo.doily.set.service;

import com.doily.demo.mapper.SetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName setService
 * @description:TODO
 * @author:lky
 * @date:2021/1/13 12:06
 */
@Service
@Transactional(readOnly = false)
public class SetService {
    @Resource
    private SetMapper setMapper;
}
