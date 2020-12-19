package com.zzlin.controller;

import com.zzlin.service.StuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20201106
 */
@RestController
public class StuFooController {

    @Resource
    private StuService stuService;

    @GetMapping("/stu/{id}")
    public Object getStu(@PathVariable("id") int id) {
        return stuService.getStuById(id);
    }
}
