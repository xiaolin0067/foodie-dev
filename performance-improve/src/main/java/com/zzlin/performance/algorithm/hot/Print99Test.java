package com.zzlin.performance.algorithm.hot;

import java.util.concurrent.Semaphore;
import lombok.SneakyThrows;

/**
 * 三个线程交替打印 1 到 99
 * 使用Semaphore
 *
 * @author pang
 * @date 2024/7/17
 */
public class Print99Test {

    private static final Semaphore SEMAPHORE_1 = new Semaphore(1);
    private static final Semaphore SEMAPHORE_2 = new Semaphore(0);
    private static final Semaphore SEMAPHORE_3 = new Semaphore(0);

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
                SEMAPHORE_1.acquire();
                if (count <= 99) {
                    System.out.println(count++ + ", " + Thread.currentThread().getName());
                }
                SEMAPHORE_2.release();
            }
        }
    }

    static class BPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                SEMAPHORE_2.acquire();
                if (count <= 99) {
                    System.out.println(count++ + ", " + Thread.currentThread().getName());
                }
                SEMAPHORE_3.release();
            }
        }
    }

    static class CPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (count <= 99) {
                SEMAPHORE_3.acquire();
                if (count <= 99) {
                    System.out.println(count++ + ", " + Thread.currentThread().getName());
                }
                SEMAPHORE_1.release();
            }
        }
    }
}


