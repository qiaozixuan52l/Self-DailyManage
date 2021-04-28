package com.doily.demo.doily.center.controller.club;

import com.doily.demo.doily.center.service.CenterService;
import com.doily.demo.doily.center.service.ConcentrateService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConcentrateController
 * @description:TODO
 * @author:lky
 * @date:2021/4/27 16:13
 */
@RestController
@RequestMapping("/concentrate")
public class ConcentrateController {
    @Resource
    private ConcentrateService concentrateService;

    @RequestMapping("/saveClock")
    public String saveClock(@RequestParam("user") String user, @RequestParam("title") String title,@RequestParam("type") int type, @RequestParam("date") Date date, @RequestParam("time") int time) {
        System.out.println(user);
        System.out.println(type + date.toString() + time);
        concentrateService.saveClock(user, type, date, time,title);
        List<Map<String, Object>> maps = this.concentrateService.findClocks();
        System.out.println(maps.toArray().toString());
        return new Gson().toJson(maps);
    }
}
