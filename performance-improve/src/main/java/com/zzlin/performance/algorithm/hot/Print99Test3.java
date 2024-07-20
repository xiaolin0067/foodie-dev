package com.zzlin.performance.algorithm.hot;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

/**
 * 三个线程交替打印 1 到 99
 *
 * @author pang
 * @date 2024/7/17
 */
public class Print99Test3 {

    private static volatile int count = 1;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(new APrinter(lock, condition), "1Printer").start();
        new Thread(new BPrinter(lock, condition), "2Printer").start();
        new Thread(new CPrinter(lock, condition), "3Printer").start();
    }

    static class APrinter implements Runnable {

        private ReentrantLock lock;
        private Condition condition;

        public APrinter(ReentrantLock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }
        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                try {
                    lock.lock();
                    while (count <= 99 && count % 3 != 1) {
                        condition.signal();
                        condition.await();
                    }
                    if (count <= 99) {
                        System.out.println(count++ + ", " + Thread.currentThread().getName());
                    }
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class BPrinter implements Runnable {

        private ReentrantLock lock;
        private Condition condition;

        public BPrinter(ReentrantLock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                try {
                    lock.lock();
                    while (count <= 99 && count % 3 != 2) {
                        condition.signal();
                        condition.await();
                    }
                    if (count <= 99) {
                        System.out.println(count++ + ", " + Thread.currentThread().getName());
                    }
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class CPrinter implements Runnable {

        private ReentrantLock lock;
        private Condition condition;

        public CPrinter(ReentrantLock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                try {
                    lock.lock();
                    while (count <= 99 && count % 3 != 0) {
                        condition.signal();
                        condition.await();
                    }
                    if (count <= 99) {
                        System.out.println(count++ + ", " + Thread.currentThread().getName());
                    }
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}


