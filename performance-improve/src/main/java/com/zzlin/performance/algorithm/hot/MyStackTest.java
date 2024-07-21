package com.zzlin.performance.algorithm.hot;

import java.util.ArrayList;
import java.util.List;

/**
 * 自己实现栈
 *
 * @author pang
 * @date 2024/7/20
 */
public class MyStackTest {

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println("---------------------");
        MyStack2 stack2 = new MyStack2();
        stack2.push(1);
        stack2.push(2);
        stack2.push(3);

        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
    }

    /**
     * list实现栈
     *
     * @author pzl on 2024/7/20 18:57
     */
    static class MyStack {
        private final List<Integer> list;
        private int topIdx;

        public MyStack() {
            this.list = new ArrayList<>();
            this.topIdx = -1;
        }

        public void push(Integer val) {
            list.add(val);
            topIdx++;
        }

        public Integer pop() {
            if (topIdx < 0) {
                return null;
            }
            return list.remove(topIdx--);
        }
    }

    /**
     * 链表实现栈
     *
     * @author pzl on 2024/7/20 18:58
     */
    static class MyStack2 {
        static class Node {
            Integer val;
            Node next;

            public Node(Integer val, Node next) {
                this.val = val;
                this.next = next;
            }
        }

        private Node topNode;

        public void push(Integer val) {
            this.topNode = new Node(val, topNode);
        }

        public Integer pop() {
            if (this.topNode == null) {
                return null;
            }
            Integer res = this.topNode.val;
            this.topNode = this.topNode.next;
            return res;
        }

    }

}
