package com.zzlin.performance.lock;

public class SynchronizedTest2 {

    public synchronized void synchronizedMethod1() {
        // 同步的代码块
        System.out.println("Method 1: Inside synchronized block");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1: Left synchronized block");
    }
 
    public synchronized void synchronizedMethod2() {
        // 另一个同步的代码块
        System.out.println("Method 2: Inside synchronized block");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2: Left synchronized block");
    }

    public synchronized static void synchronizedMethod3() {
        // 另一个同步的代码块
        System.out.println("Method 3: Inside synchronized block");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 3: Left synchronized block");
    }

    public static void main(String[] args) {
        SynchronizedTest2 synchronizedTest2 = new SynchronizedTest2();
        Thread t1 = new Thread(synchronizedTest2::synchronizedMethod1);
        Thread t2 = new Thread(synchronizedTest2::synchronizedMethod2);
        Thread t3 = new Thread(SynchronizedTest2::synchronizedMethod3);

        t1.start();
        t2.start();
        t3.start();
    }
}
 
