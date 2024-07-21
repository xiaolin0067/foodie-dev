package com.zzlin.performance.algorithm.hot;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.SneakyThrows;

/**
 * 生产者消费者模型
 *
 * @author pang
 * @date 2024/7/21
 */
public class ProductAndConsumerTest {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10000);

        new Thread(new Product(queue)).start();
        new Thread(new Consumer(queue)).start();
    }

    static class Product implements Runnable {

        private final BlockingQueue<Integer> queue;

        public Product(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @SneakyThrows
        @Override
        public void run() {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                int sleepTime = random.nextInt(10000);
                Thread.sleep(sleepTime);
                System.out.println(sleepTime + ", product " + Thread.currentThread().getName());
                queue.add(sleepTime);
            }
        }

    }

    static class Consumer implements Runnable {

        private final BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Integer take = queue.take();
                System.out.println(take + ", consumer " + Thread.currentThread().getName());
            }
        }
    }
}
