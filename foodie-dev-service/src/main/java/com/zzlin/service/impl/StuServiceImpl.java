package com.zzlin.service.impl;

import com.zzlin.mapper.StuMapper;
import com.zzlin.pojo.Stu;
import com.zzlin.service.StuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20201106
 */
@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuById(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveParent() {
        Stu stu = new Stu();
        stu.setName("parent");
        stu.setAge(19);
        stuMapper.insert(stu);
    }

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void saveChildren() {
        saveChild1();
        int a = 1 / 0;
        saveChild2();
    }

    private void saveChild1() {
        Stu stu1 = new Stu();
        stu1.setName("child-1");
        stu1.setAge(11);
        stuMapper.insert(stu1);
    }

    private void saveChild2() {
        Stu stu2 = new Stu();
        stu2.setName("child-2");
        stu2.setAge(22);
        stuMapper.insert(stu2);
    }

    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setAge(18);
        stu.setName("王大锤");
        stuMapper.insert(stu);
    }

    @Override
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setAge(18);
        stu.setName("王大锤");
        stu.setId(id);
        stuMapper.updateByPrimaryKey(stu);
    }

    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);
    }
}
