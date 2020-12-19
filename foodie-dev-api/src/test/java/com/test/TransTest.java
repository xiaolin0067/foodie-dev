package com.test;

import com.zzlin.App;
import com.zzlin.service.TestTransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20201106
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TransTest {

    @Resource
    private TestTransService testTransService;

    @Test
    public void myTest(){
        testTransService.testPropagationTrans();
    }
}
