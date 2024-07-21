package com.zzlin.performance.algorithm.hot;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用队列实现栈
 *
 * @author pang
 * @date 2024/7/20
 */
public class QueueToStackTest {

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    static class MyStack {
        private final Queue<Integer> queue;

        public MyStack() {
            this.queue = new LinkedList<>();
        }

        public void push(Integer val) {
            queue.add(val);
        }

        public Integer pop() {
            if (queue.isEmpty()) {
                return null;
            }
            for (int i = 1; i < queue.size(); i++) {
                queue.add(queue.poll());
            }
            return queue.poll();
        }
    }

}
