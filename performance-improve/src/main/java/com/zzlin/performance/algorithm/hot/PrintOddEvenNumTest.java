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

    private static final Object LOCK = new Object();
    private static final int MAX = 100;
    private static final AtomicInteger NUM = new AtomicInteger(1);

    public static void main(String[] args) {
        new Thread(new OddPrinter()).start();
        new Thread(new EvenPrinter()).start();
    }

    static class OddPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    if (NUM.get() % 2 == 0) {
                        LOCK.notify();
                        LOCK.wait();
                    } else {
                        if (NUM.get() > MAX) {
                            break;
                        }
                        System.out.println(NUM.getAndAdd(1));
                    }
                }
            }
        }
    }

    static class EvenPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    if (NUM.get() % 2 != 0) {
                        LOCK.notify();
                        LOCK.wait();
                    } else {
                        if (NUM.get() > MAX) {
                            break;
                        }
                        System.out.println(NUM.getAndAdd(1));
                    }
                }
            }
        }
    }
}


