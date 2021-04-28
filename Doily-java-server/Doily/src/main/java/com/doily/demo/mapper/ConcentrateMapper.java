package com.doily.demo.mapper;

import com.doily.demo.entity.Clock;

import java.util.Date;
import java.util.List;


public interface ConcentrateMapper {
    boolean saveClock(String user, int type, Date date, int time, String title);

    List<Clock> findClocks();
}
