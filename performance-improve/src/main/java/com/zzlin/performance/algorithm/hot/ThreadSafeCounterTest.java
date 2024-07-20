package com.zzlin.performance.algorithm.hot;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import sun.misc.Unsafe;

/**
 * @author pang
 * @date 2024/7/20
 */
public class ThreadSafeCounterTest {

    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        Counter1 counter = new Counter1(0);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increase();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(counter.get());
    }

    static class Counter {
        private volatile int count;

        public Counter(int init) {
            count = init;
        }

        public synchronized void increase() {
            System.out.println(Thread.currentThread().getName() + " increase");
            count++;
        }

        public int get() {
            return count;
        }
    }

    static class Counter1 {
        private volatile int count;
        private long countOffset;
        private Unsafe unsafe;

        @SneakyThrows
        public Counter1(int init) {
            count = init;
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            this.unsafe = (Unsafe) theUnsafe.get(null);
            Field countField = Counter1.class.getDeclaredField("count");
            countField.setAccessible(true);
            this.countOffset = this.unsafe.objectFieldOffset(countField);
        }

        public void increase() {
            boolean success;
            do {
                // 修改哪个对象(var1)的哪个字段的偏移量(var2)，期望值(var4)，修改后的值(var5)
                success = unsafe.compareAndSwapInt(this, countOffset, count, count + 1);
                if (!success) {
                    System.out.println("CAS失败 " + Thread.currentThread().getName());
                }
            } while (!success);
        }

        public int get() {
            return count;
        }
    }
}
