package com.zzlin.performance.algorithm.hot;

import java.util.concurrent.CyclicBarrier;
import lombok.SneakyThrows;

/**
 * 互斥：资源必须处于非共享模式，即一次只有一个进程可以使用。如果另一进程申请该资源，那么必须等待直到该资源被释放为止。
 * 占有并等待：一个进程至少应该占有一个资源，并等待另一资源，而该资源被其他进程所占有。
 * 不可抢占：资源不能被抢占。只能在持有资源的进程完成任务后，该资源才会被释放。
 * 循环等待：有一组等待进程 {P0, P1,..., Pn}， P0 等待的资源被 P1 占有，P1 等待的资源被 P2 占有，……，Pn-1 等待的资源被 Pn 占有，Pn 等待的资源被 P0 占有。
 * 注意 ⚠️：这四个条件是产生死锁的 必要条件 ，也就是说只要系统发生死锁，这些条件必然成立，而只要上述条件之一不满足，就不会发生死锁。
 * 数据库死锁的例子：
 *  -- 事务1
 *  START TRANSACTION;
 *  SELECT * FROM test WHERE id >= 1 AND id <= 3 FOR UPDATE;
 *  -- 事务2
 *  START TRANSACTION;
 *  INSERT INTO example_gap (id, value) VALUES (2, 20);
 * @author pang
 * @date 2024/7/21
 */
public class DeadLockTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Object lock1 = new Object();
        Object lock2 = new Object();

        // exp1
//        new Thread(new Thread1(lock1, lock2)).start();
//        new Thread(new Thread1(lock2, lock1)).start();
        // exp2
        Thread t1 = new Thread(new Thread1(lock1, lock2, cyclicBarrier));
        Thread t2 = new Thread(new Thread2(lock1, lock2, cyclicBarrier));
        t1.start();
        t2.start();
    }

    static class Thread1 implements Runnable {

        private final Object lock1;
        private final Object lock2;
        private final CyclicBarrier cyclicBarrier;

        public Thread1(Object lock1, Object lock2, CyclicBarrier cyclicBarrier) {
            this.lock1 = lock1;
            this.lock2 = lock2;
            this.cyclicBarrier = cyclicBarrier;
        }

        @SneakyThrows
        @Override
        public void run() {
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + ", 开始抢占" + lock1.hashCode());
            synchronized (lock1) {
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName() + ", 获得了" + lock1.hashCode());
                System.out.println(Thread.currentThread().getName() + ", 开始抢占" + lock2.hashCode());
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + ", 获得了" + lock2.hashCode());
                }
            }
        }
    }

    static class Thread2 implements Runnable {

        private final Object lock1;
        private final Object lock2;
        private final CyclicBarrier cyclicBarrier;

        public Thread2(Object lock1, Object lock2, CyclicBarrier cyclicBarrier) {
            this.lock1 = lock1;
            this.lock2 = lock2;
            this.cyclicBarrier = cyclicBarrier;
        }

        @SneakyThrows
        @Override
        public void run() {
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + ", 开始抢占" + lock2.hashCode());
            synchronized (lock2) {
                Thread.sleep(10);
                System.out.println(Thread.currentThread().getName() + ", 获得了" + lock2.hashCode());
                System.out.println(Thread.currentThread().getName() + ", 开始抢占" + lock1.hashCode());
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + ", 获得了" + lock1.hashCode());
                }
            }
        }
    }

}
