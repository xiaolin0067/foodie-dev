package com.zzlin.performance.algorithm.hot;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 用栈实现队列
 *
 * @author pang
 * @date 2024/7/20
 */
public class StackToQueueTest {

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println(queue.poll());
        queue.add(4);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        queue.add(5);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    static class MyQueue {
        private final Deque<Integer> stack;

        public MyQueue() {
            this.stack = new ArrayDeque<>();
        }

        public void add(Integer val) {
            stack.push(val);
        }

        public Integer poll() {
            if (stack.isEmpty()) {
                return null;
            }
            Deque<Integer> tmpStack = new ArrayDeque<>();
            while (stack.size() > 1) {
                tmpStack.push(stack.pop());
            }
            Integer res = stack.pop();
            while (!tmpStack.isEmpty()) {
                stack.push(tmpStack.pop());
            }
            return res;
        }
    }

}
