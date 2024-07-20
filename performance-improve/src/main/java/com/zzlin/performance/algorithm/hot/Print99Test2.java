package com.zzlin.performance.algorithm.hot;

import java.util.concurrent.Semaphore;
import lombok.SneakyThrows;

/**
 * 三个线程交替打印 1 到 99
 * wait() notifyAll()
 *
 * @author pang
 * @date 2024/7/17
 */
public class Print99Test2 {

    private static final Object LOCK = new Object();

    private static volatile int count = 1;

    public static void main(String[] args) {
        new Thread(new APrinter(), "1Printer").start();
        new Thread(new BPrinter(), "2Printer").start();
        new Thread(new CPrinter(), "3Printer").start();
    }

    static class APrinter implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                synchronized (LOCK) {
                    if (count % 3 != 1) {
                        LOCK.wait();
                    } else {
                        if (count <= 99) {
                            System.out.println(count++ + ", " + Thread.currentThread().getName());
                        }
                        LOCK.notifyAll();
                    }
                }

            }
        }
    }

    static class BPrinter implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                synchronized (LOCK) {
                    if (count % 3 != 2) {
                        LOCK.wait();
                    } else {
                        if (count <= 99) {
                            System.out.println(count++ + ", " + Thread.currentThread().getName());
                        }
                        LOCK.notifyAll();
                    }
                }

            }
        }
    }

    static class CPrinter implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                synchronized (LOCK) {
                    if (count % 3 != 0) {
                        LOCK.wait();
                    } else {
                        if (count <= 99) {
                            System.out.println(count++ + ", " + Thread.currentThread().getName());
                        }
                        LOCK.notifyAll();
                    }
                }

            }
        }
    }
}


