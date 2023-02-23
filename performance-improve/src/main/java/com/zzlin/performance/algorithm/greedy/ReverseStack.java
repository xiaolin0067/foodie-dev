package com.zzlin.performance.algorithm.greedy;

import java.util.Stack;

/**
 * 给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数如何实现?
 *
 * @author zlin
 * @date 20230223
 */
public class ReverseStack {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        for (Integer i : stack) {
            System.out.println(i);
        }
        System.out.println("-------------------");
        reverse(stack);
        for (Integer i : stack) {
            System.out.println(i);
        }
    }

    /**
     * 逆序栈
     *
     * | 1 |         | 3 |
     * | 2 |    =>   | 2 |
     * | 3 |         | 1 |
     * ----          ----
     */
    private static void reverse(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        int last = f(stack);
        reverse(stack);
        stack.push(last);
    }

    /**
     * stack
     *
     * | 1 |         |   |
     * | 2 |    =>   | 1 |
     * | 3 |         | 2 |
     * ----          ----
     *
     * @return 栈底（如上例子返回3）
     */
    private static int f(Stack<Integer> stack) {
        int item = stack.pop();
        if (stack.isEmpty()) {
            return item;
        } else {
            int last = f(stack);
            stack.push(item);
            return last;
        }
    }

}
