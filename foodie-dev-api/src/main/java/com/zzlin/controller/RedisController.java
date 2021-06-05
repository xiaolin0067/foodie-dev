package com.zzlin.controller;

import com.zzlin.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author zlin
 * @date 20210605
 */
@ApiIgnore
@RequestMapping("redis")
@RestController
public class RedisController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

    @Autowired
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

}
