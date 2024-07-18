package com.zzlin.performance.algorithm.hot;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程，一个打印奇数，一个打印偶数
 *
 * @author pang
 * @date 2024/7/17
 */
public class PrintOddEvenNumTest1 {

    private static final Semaphore semaphore = new Semaphore(1);
    private static final AtomicInteger COUNT = new AtomicInteger(1);

    public static void main(String[] args) {
        new Thread(new OddPrinter(), "OddPrinter").start();
        new Thread(new EvenPrinter(), "EvenPrinter").start();
    }

    static class OddPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (COUNT.get() <= 100) {
                semaphore.acquire();
                if (COUNT.get() <= 100 && COUNT.get() % 2 != 0) {
                    System.out.println(COUNT.getAndAdd(1) + ", " + Thread.currentThread().getName());
                }
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + "exit");
        }
    }

    static class EvenPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (COUNT.get() <= 100) {
                semaphore.acquire();
                if (COUNT.get() <= 100 && COUNT.get() % 2 == 0) {
                    System.out.println(COUNT.getAndAdd(1) + ", " + Thread.currentThread().getName());
                }
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + "exit");
        }
    }
}


