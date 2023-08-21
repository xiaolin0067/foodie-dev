package com.zzlin.performance.algorithm.base.struct.list;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制含有随机指针节点的链表
 * @author zlin
 * @date 20220707
 */
public class CopyListWithRandom {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        n1.next = n2;
        n2.next = n3;
        n1.rand = n3;
        n2.rand = n1;
        printList(n1);
        System.out.println("方法一");
        printList(copyListWithRandom1(n1));
        System.out.println("方法二");
        printList(copyListWithRandom2(n1));

    }

    public static class Node {
        public int value;
        public Node next;
        public Node rand;
        public Node(int val) {
            value = val;
        }
    }

    /**
     * 使用map实现 复制含有随机指针节点的链表
     */
    public static Node copyListWithRandom1(Node head) {
        if (head == null) {
            return null;
        }
        // 这里map存的是内存地址
        Map<Node, Node> map = new HashMap<>();
        // 给链表所有节点复制一个值到map的value
        Node cur = head;
        while (cur != null) {
            Node node = new Node(cur.value);
            map.put(cur, node);
            cur = cur.next;
        }
        // 使用原链表的next和rand关系来给新链表赋值
        cur = head;
        while (cur != null) {
            Node copy = map.get(cur);
            Node next = map.get(cur.next);
            Node rand = map.get(cur.rand);
            copy.next = next;
            copy.rand = rand;
            cur = cur.next;
        }
        // 返回新链表的head
        return map.get(head);
    }

    /**
     * 将复制的节点放到原来的节点之后，然后通过原来的节点找到他的rand指针
     * rand: 1->3, 2->1, 3->null
     * _______
     * |     |
     * 1->2->3->null
     * |  |  |
     * ---  null
     *
     * rand: 1' = 1 rand 3 next 3'
     *     ______________
     * ____|___________ |
     * |   |          | |
     * 1->1'->2->2'->3->3'->null
     * 将所有复制节点的next都赋值完成后，在拆回成两个链表，返回1'
     */
    public static Node copyListWithRandom2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        // 在原节点后一个节点上插入新节点
        Node next = null, copyNode = null;
        while (cur != null) {
            next = cur.next;
            copyNode = new Node(cur.value);
            cur.next = copyNode;
            copyNode.next = next;
            cur = next;
        }
        // 复制rand节点关系
        cur = head;
        while (cur != null) {
            copyNode = cur.next;
            next = copyNode.next;
            copyNode.rand = cur.rand == null ? null : cur.rand.next;
            cur = next;
        }
        // 此时复制的头节点在原链表头结点的next，记录该头结点，还原成两个链表关系后返回该头结点
        Node res = head.next;
        // 还原成两个链表
        cur = head;
        while (cur != null) {
            copyNode = cur.next;
            next = copyNode.next;
            cur.next = next;
            copyNode.next = next == null ? null : next.next;
            cur = next;
        }
        return res;
    }

    public static void printList(Node head) {
        if (head == null) {
            return;
        }
        while (head != null) {
            System.out.println(head + ", value=" + head.value +
                    ", next=" + head.next +
                    ", rand=" + head.rand);
            head = head.next;
        }
    }

}
