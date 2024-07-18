package com.zzlin.performance.algorithm.hot;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

/**
 * 两个线程，一个打印1,2,3，一个打印A,B,C
 *
 * @author pang
 * @date 2024/7/17
 */
public class PrintAbcTest1 {

    private static final Semaphore A_SEMAPHORE = new Semaphore(1);
    private static final Semaphore B_SEMAPHORE = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(new N123Printer(), "N123Printer").start();
        new Thread(new ABCPrinter(), "ABCPrinter").start();
    }

    static class N123Printer implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            int num = 1;
            while (true) {
                A_SEMAPHORE.acquire();
                if (num == 4) {
                    num = 1;
                }
                System.out.println(num++ + ", " + Thread.currentThread().getName());
                B_SEMAPHORE.release();
            }
        }
    }

    static class ABCPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            char[] charArray = new char[]{'A', 'B', 'C'};
            int idx = 0;
            while (true) {
                B_SEMAPHORE.acquire();
                if (idx == 3) {
                    idx = 0;
                }
                System.out.println(charArray[idx++] + ", " + Thread.currentThread().getName());
                A_SEMAPHORE.release();
            }
        }
    }
}


