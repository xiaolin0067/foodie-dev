package com.zzlin.controller;

import com.zzlin.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author zlin
 * @date 20210605
 */
@ApiIgnore
@RequestMapping("redis")
@RestController
public class RedisController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

    @Resource
    private RedisOperator redisOperator;

    @GetMapping("/set")
    public Object set(String key, String value) {
        // opsForValue() 处理字符串的value
//        redisTemplate.opsForValue().set(key, value);

        redisOperator.set(key, value);
        return "OK";
    }

    @GetMapping("/get")
    public String get(String key) {
//        return (String) redisTemplate.opsForValue().get(key);
        return redisOperator.get(key);
    }

    @GetMapping("/del")
    public Object del(String key) {
//        redisTemplate.delete(key);
        redisOperator.del(key);
        return "OK";
    }

    @GetMapping("/mget")
    public Object mget(String... keys) {
        return redisOperator.mget(Arrays.asList(keys));
    }

    /**
     * 批量查询-使用redis pipeline
     * 使用pipeline可支持更加丰富的批量操作，同时可插入修改以及对key对value进行处理，而mget只支持字符串的批量查询
     */
    @GetMapping("/batchGet")
    public Object batchGet(String... keys) {
        return redisOperator.batchGet(Arrays.asList(keys));
    }

}
