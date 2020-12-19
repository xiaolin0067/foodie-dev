package com.zzlin.service;

import com.zzlin.pojo.Stu;

/**
 * @author zlin
 * @date 20201106
 */
public interface StuService {

    Stu getStuById(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);

    void saveParent();

    void saveChildren();
}
