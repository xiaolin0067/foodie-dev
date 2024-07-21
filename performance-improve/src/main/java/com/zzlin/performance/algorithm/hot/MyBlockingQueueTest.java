package com.zzlin.performance.algorithm.hot;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

/**
 * @author pang
 * @date 2024/7/21
 */
public class MyBlockingQueueTest {

    public static void main(String[] args) {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(5);

        new Thread(new Product(queue)).start();
        new Thread(new Consumer(queue)).start();
    }

    static class Product implements Runnable {

        private final MyBlockingQueue<Integer> queue;

        public Product(MyBlockingQueue<Integer> queue) {
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
                queue.put(sleepTime);
            }
        }

    }

    static class Consumer implements Runnable {

        private final MyBlockingQueue<Integer> queue;

        public Consumer(MyBlockingQueue<Integer> queue) {
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

    static class MyBlockingQueue<T> {
        static class Node<T> {
            T value;
            Node<T> next;

            public Node(T t) {
                this.value = t;
            }
        }

        Node<T> head;
        Node<T> tail;

        final int capacity;

        AtomicInteger curCapacity = new AtomicInteger(0);

        public MyBlockingQueue(int capacity) {
            this.capacity = capacity;
        }

        private final ReentrantLock lock = new ReentrantLock();
        private final Condition conditionPut = lock.newCondition();
        private final Condition conditionTake = lock.newCondition();

        @SneakyThrows
        public void put(T data) {
            try {
                lock.lock();
                while (curCapacity.get() + 1 > capacity) {
                    System.out.println("队列已满");
                    conditionPut.await();
                }
                Node<T> node = new Node<>(data);
                if (head == null) {
                    head = tail = node;
                } else {
                    tail = tail.next = node;
                }
                curCapacity.incrementAndGet();
                conditionTake.signal();
            } finally {
                lock.unlock();
            }
        }

        @SneakyThrows
        public T take() {
            try {
                lock.lock();
                while (curCapacity.get() < 1) {
                    System.out.println("空队列，等待");
                    conditionTake.await();
                }
                T res = head.value;
                head = head.next;
                curCapacity.decrementAndGet();
                conditionPut.signal();
                return res;
            } finally {
                lock.unlock();
            }
        }
    }

}
