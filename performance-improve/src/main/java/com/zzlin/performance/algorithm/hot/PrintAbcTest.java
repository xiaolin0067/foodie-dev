package com.zzlin.performance.algorithm.hot;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

/**
 * 交替打印ABC
 *
 * @author pang
 * @date 2024/7/17
 */
public class PrintAbcTest {

    private static final Semaphore A_SEMAPHORE = new Semaphore(1);
    private static final Semaphore B_SEMAPHORE = new Semaphore(0);
    private static final Semaphore C_SEMAPHORE = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(new APrinter(), "APrinter").start();
        new Thread(new BPrinter(), "BPrinter").start();
        new Thread(new CPrinter(), "CPrinter").start();
    }

    static class APrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                A_SEMAPHORE.acquire();
                System.out.println("A, " + Thread.currentThread().getName());
                B_SEMAPHORE.release();
            }
        }
    }

    static class BPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                B_SEMAPHORE.acquire();
                System.out.println("B, " + Thread.currentThread().getName());
                C_SEMAPHORE.release();
            }
        }
    }

    static class CPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                C_SEMAPHORE.acquire();
                System.out.println("C, " + Thread.currentThread().getName());
                A_SEMAPHORE.release();
            }
        }
    }
}


