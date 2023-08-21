package com.zzlin.performance.algorithm.base.struct.list;

import java.util.Stack;

/**
 * 单链表是否回文
 * @author zlin
 * @date 20220706
 */
public class IsPalindromeList {

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(3);
        Node node5 = new Node(2);
        Node node6 = new Node(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        System.out.println(isPalindrome1(node1));
        System.out.println(isPalindrome2(node1));
        System.out.println(isPalindrome3(node1));
        Node n = node1;
        while (n != null) {
            System.out.print(n.val + ", ");
            n = n.next;
        }
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 单链表是否回文
     * 将链表节点都放入栈中，然后从链表头开始遍历与栈弹出节点比对，若存在不一致就返回false
     * 额外空间：O(n)
     */
    public static boolean isPalindrome1(Node head) {
        if (head == null) {
            return false;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 单链表是否回文
     * 快慢指针
     * 满指针一次走一步，快指针一次走两步，直到结束。
     * 将慢指针到结束的节点入栈，然后依次出栈和头结点比较，若存在不相等的返回false
     * 额外空间：O(n/2)
     */
    public static boolean isPalindrome2(Node head) {
        if (head == null) {
            return false;
        }
        if (head.next == null) {
            return true;
        }
        // 慢指针为快指针的一半加一，会节省一个空间
        // 也可快慢指针都指定为head，不影响结果
        Node slow = head.next;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Stack<Node> stack = new Stack<>();
        while (slow != null) {
            stack.push(slow);
            slow = slow.next;
        }
        while (!stack.isEmpty()) {
            if (stack.pop().val != head.val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 单链表是否回文
     * 快慢指针
     * 满指针一次走一步，快指针一次走两步，直到结束。
     * 将慢指针到结束之间的节点逆序，然后在遍历对比，结束后将后半部分链表指针恢复，返回对比结果
     * 1->2->3->2->1
     * 1->2->3<-2<-1
     * 还原后再返回比对结果
     * 1->2->3->2->1
     * 额外空间：O(1)
     */
    public static boolean isPalindrome3(Node head) {
        if (head == null) {
            return false;
        }
        if (head.next == null) {
            return true;
        }
        Node n1 = head;
        Node n2 = head;
        while (n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }
        n2 = n1.next;
        // 中间节点，置为null避免两个节点成环，此节点为两个链表的尾结点
        n1.next = null;
        Node n3;
        // 1->2->3<-2->1
        //       ^  ^  ^
        //       |  |  |
        //       n1 n2 n3
        while (n2 != null) {
            // 记录下一个节点
            n3 = n2.next;
            // 逆序当前节点
            n2.next = n1;
            // 左侧节点和中间节点前移一步
            n1 = n2;
            n2 = n3;
        }
        // n2节点为null了，他的前一个节点n1为最后一个节点，记录最后一个节点
        n3 = n1;
        n2 = head;
        // 1->2->3<-2<-1
        // ^           ^
        // |           |
        // n2          n1/n3
        boolean res = true;
        while (n1 != null && n2 != null) {
            if (n1.val != n2.val) {
                res = false;
                break;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        // 得到回文结果res，因有break，n1、n2位置随机未知，用n3位置把后面的逆序链表还原即可
        // 1->2->3<-2<-1
        //       ^  ^  ^
        //       |  |  |
        //       n1 n2 n3
        n2 = n3.next;
        // 避免两个节点成环
        n3.next = null;
        while (n2 != null) {
            n1 = n2.next;
            n2.next = n3;
            n3 = n2;
            n2 = n1;
        }
        return res;
    }
}
