package com.zzlin.performance.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zlin
 * @date 20220326
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        SynchronizedTest t1 = new SynchronizedTest();
        SynchronizedTest t2 = new SynchronizedTest();
        new Thread(() -> t1.synchronizedThisTest(),"thread1").start();
        new Thread(() -> t2.synchronizedThisTest(),"thread2").start();
    }
    private void synchronizedThisTest() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "-----START------" + System.currentTimeMillis());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "-----END------" + System.currentTimeMillis());
        }
    }

    /**
     * 变量逃逸
     */
    private Object object;
    public void someMethod2() {
        object = someMethod1();
    }
    public Object someMethod1() {
        // 变量逃逸
        Object object = new Object();
        synchronized (object) {
            System.out.println(object);
            return object;
        }
    }

    /**
     * 锁消除
     */
    public void someMethod() {
        Object object = new Object();
        synchronized (object) {
            System.out.println(object);
        }
    }

    /**
     * 锁粗化
     */
    public void lockCoarsening() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            synchronized (list) {
                list.add(i);
            }
        }
    }
     //相当于如下
    public void lockCoarsening1() {
        List<Integer> list = new ArrayList<>();
        synchronized (list) {
            for (int i = 0; i < 10000000; i++) {
                list.add(i);
            }
        }
    }

}
