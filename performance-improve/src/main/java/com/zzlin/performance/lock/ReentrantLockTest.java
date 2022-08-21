package com.zzlin.performance.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zlin
 * @date 20220806
 */
public class ReentrantLockTest {

    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockTest tl = new ReentrantLockTest();
        new Thread(() -> tl.testReentrantLock(), "thread-1").start();
        new Thread(() -> tl.testReentrantLock(), "thread-2").start();
    }

    private void testReentrantLock() {
        System.out.println("进入了方法" + Thread.currentThread().getName());
        lock.lock();
        try {
            System.out.println("获取到了锁" + Thread.currentThread().getName());
            Thread.sleep(3000);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("释放锁" + Thread.currentThread().getName());
        }
    }

}
