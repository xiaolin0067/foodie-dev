package com.zzlin.performance.algorithm.hot;

import lombok.SneakyThrows;

/**
 * 两个线程，一个打印奇数，一个打印偶数
 *
 * @author pang
 * @date 2024/7/17
 */
public class PrintAbcTest3 {

    private static final Object LOCK = new Object();
    private static volatile Boolean printNum = true;

    public static void main(String[] args) {
        new Thread(new N123Printer(), "N123Printer").start();
        new Thread(new ABCPrinter(), "ABCPrinter").start();
    }

    static class N123Printer implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            int num = 1;
            while (num <= 3) {
                synchronized (LOCK) {
                    if (Boolean.FALSE.equals(printNum)) {
                        LOCK.wait();
                    } else {
                        System.out.println(num++ + ", " + Thread.currentThread().getName());
                        printNum = false;
                        LOCK.notifyAll();
                    }
                }
            }
        }
    }

    static class ABCPrinter implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            char[] strArr = new char[]{'A', 'B', 'C'};
            int idx = 0;
            while (idx <= 2) {
                synchronized (LOCK) {
                    if (Boolean.TRUE.equals(printNum)) {
                        LOCK.wait();
                    } else {
                        System.out.println(strArr[idx++] + ", " + Thread.currentThread().getName());
                        printNum = true;
                        LOCK.notifyAll();
                    }
                }
            }
        }
    }
}


