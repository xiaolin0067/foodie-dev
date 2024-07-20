package com.zzlin.performance.algorithm.hot;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

/**
 * 三个线程交替打印 1 到 99
 * Condition优化版本
 *
 * @author pang
 * @date 2024/7/17
 */
public class Print99Test3 {

    private static volatile int count = 1;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        new Thread(new APrinter(lock, condition1, condition2), "1Printer").start();
        new Thread(new BPrinter(lock, condition2, condition3), "2Printer").start();
        new Thread(new CPrinter(lock, condition3, condition1), "3Printer").start();
    }

    static class APrinter implements Runnable {

        private final ReentrantLock lock;
        private final Condition condition;
        private final Condition nextCondition;

        public APrinter(ReentrantLock lock, Condition condition, Condition nextCondition) {
            this.lock = lock;
            this.condition = condition;
            this.nextCondition = nextCondition;
        }
        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                try {
                    lock.lock();
                    if (count % 3 != 1) {
                        condition.await();
                    }
                    if (count <= 99) {
                        System.out.println(count++ + ", " + Thread.currentThread().getName());
                    }
                    nextCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class BPrinter implements Runnable {

        private final ReentrantLock lock;
        private final Condition condition;
        private final Condition nextCondition;

        public BPrinter(ReentrantLock lock, Condition condition, Condition nextCondition) {
            this.lock = lock;
            this.condition = condition;
            this.nextCondition = nextCondition;
        }
        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                try {
                    lock.lock();
                    if (count % 3 != 2) {
                        condition.await();
                    }
                    if (count <= 99) {
                        System.out.println(count++ + ", " + Thread.currentThread().getName());
                    }
                    nextCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class CPrinter implements Runnable {

        private final ReentrantLock lock;
        private final Condition condition;
        private final Condition nextCondition;

        public CPrinter(ReentrantLock lock, Condition condition, Condition nextCondition) {
            this.lock = lock;
            this.condition = condition;
            this.nextCondition = nextCondition;
        }
        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                try {
                    lock.lock();
                    if (count % 3 != 0) {
                        condition.await();
                    }
                    if (count <= 99) {
                        System.out.println(count++ + ", " + Thread.currentThread().getName());
                    }
                    nextCondition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}


