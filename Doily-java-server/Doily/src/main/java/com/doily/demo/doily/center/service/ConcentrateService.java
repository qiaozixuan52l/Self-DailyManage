package com.doily.demo.doily.center.service;

import com.doily.demo.entity.Clock;
import com.doily.demo.mapper.ConcentrateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName ConcentrateService
 * @description:TODO
 * @author:lky
 * @date:2021/4/27 16:22
 */
@Service
public class ConcentrateService {
    @Resource
    private ConcentrateMapper concentrateMapper;

    public boolean saveClock(String user, int type, Date date, int time, String title) {
        return concentrateMapper.saveClock(user, type, date, time,title);
    }

    public List<Map<String, Object>> findClocks() {
        List<Clock>clocks=concentrateMapper.findClocks();
        List<Map<String, Object>> resultSet = new ArrayList<>();
        for (Clock clock: clocks) {
            Map<String, Object> map = new HashMap<>();
            map.put("user", clock.getUser());
            map.put("time", clock.getTime());
            map.put("date", clock.getDate().toString());
            map.put("type", clock.getType());
            map.put("title", clock.getTitle());
            map.put("id", clock.getId());
            resultSet.add(map);
        }
        return resultSet;
    }
}
