package com.zzlin.performance.algorithm.hot;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程，一个打印奇数，一个打印偶数
 *
 * @author pang
 * @date 2024/7/17
 */
public class PrintOddEvenNumTest {

    private static final int MAX = 100;

    private static final Object LOCK = new Object();

    private static final AtomicInteger COUNT = new AtomicInteger(1);

    public static void main(String[] args) {
        new Thread(new OddPrinter(), "OddPrinter").start();
        new Thread(new EvenPrinter(), "EvenPrinter").start();
    }

    static class OddPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (COUNT.get() <= MAX) {
                synchronized (LOCK) {
                    if (COUNT.get() % 2 == 0) {
                        LOCK.wait();
                    } else {
                        System.out.println(COUNT.getAndAdd(1) + ", " + Thread.currentThread().getName());
                        LOCK.notify();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "exit");
        }
    }

    static class EvenPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (COUNT.get() <= MAX) {
                synchronized (LOCK) {
                    if (COUNT.get() % 2 != 0) {
                        LOCK.wait();
                    } else {
                        System.out.println(COUNT.getAndAdd(1) + ", " + Thread.currentThread().getName());
                        LOCK.notify();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "exit");
        }
    }
}


