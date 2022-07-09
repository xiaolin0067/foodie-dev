package com.zzlin.performance.algorithm.struct.list;

/**
 * 找到两个链表的第一个相交的节点
 * 单链表最难题目
 * @author zlin
 * @date 20220707
 */
public class FindFirstIntersectNode {

    public static void main(String[] args) {
        // 无环 n1
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        // 尾结点一样: m1, m2
        Node m1 = new Node(1);
        m1.next = n2;
        Node m2 = n1;
        // 尾节点不一样, m3
        Node m3 = new Node(9);
        printVal(getFirstIntersectNode(n1, m1));
        printVal(getFirstIntersectNode(n1, m2));
        printVal(getFirstIntersectNode(n1, m3));
        // 有环 l1
        Node l1 = new Node(1);
        Node l2 = new Node(2);
        Node l3 = new Node(3);
        Node l4 = new Node(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l2;
        // 入环节点一样, j1, j2
        Node j1 = new Node(5);
        j1.next = l1;
        Node j2 = l2;
        // 入环节点不一样
        // 不相交 p1
        Node p1 = new Node(1);
        Node p2 = new Node(2);
        Node p3 = new Node(3);
        Node p4 = new Node(4);
        p1.next = p2;
        p2.next = p3;
        p3.next = p4;
        p4.next = p2;
        // 相交 o1
        Node o1 = new Node(9);
        o1.next = l3;
        printVal(getFirstIntersectNode(l1, j1));
        printVal(getFirstIntersectNode(l1, j2));
        printVal(getFirstIntersectNode(l1, p1));
        printVal(getFirstIntersectNode(l1, o1));

    }

    /**
     * 找到两个链表的第一个相交的节点
     * 时间复杂度：O(n)
     * 空间复杂性：O(1)
     */
    public static Node getFirstIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        // 单链表，一个有环一个无环一定不相交
        return null;
    }

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 找到入环节点，无环则返回null
     *   若存在环，快慢指针从头节点开始走，慢节点每次1步，快节点每次2步，则快慢指针一定会在某个时候相遇，且快慢指针重复会小于等于两圈
     *     若快节点指向了null，则代表无环
     *   慢指针停留在环中相遇的位置，快指针回到链表开始位置，都每次只走一步，再次相遇的节点必定是入环节点
     *   入环节点：如下有环单链表中3即入环节点
     *   1->2->3->4->5
     *         ^     |
     *         |_____|
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // 快慢指针先各往前走一次，若成环，找到成环的相遇节点
        Node slow = head.next, fast = head.next.next;
        while (slow != fast) {
            // 若有next为null，代表可走到尾，不成环
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        // 从头节点和相遇节点开始各走一步，再次相遇的节点就是入环节点
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    /**
     * 一、若两个链表都不是环，找到相交节点并返回
     * 遍历两个链表到结束，记录两个链表的长度和尾节点
     * 若尾结点不同，不相交，返回null
     * 若尾结点相同，相交
     *   长链表从头开始先走两个链表长度的差值
     *   长链表和短链表同时往下走，相遇的节点则为第一个相交节点
     *   n
     *   |
     *   n  n
     *   | /
     *   n
     *   |
     *   n
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        // len = head1.len - head2.len
        int len = 0;
        while (cur1.next != null) {
            len++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            len--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        cur1 = len > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        len = Math.abs(len);
        while (len > 0) {
            len--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 二、两个有环链表，找到相交节点并返回
     * 其关系有以下三种情况：
     * 1、不相交，返回null
     * n      n
     * |      |
     * n___n  n___n
     * |   |  |   |
     * n___n  n___n
     * 2、入环节点是同一个，找第一个相交节点返回
     * n  n
     * | /
     * n
     * |
     * n___n
     * |   |
     * n___n
     * 3、入环节点不是同一个，两个入环节点都算是相交节点
     * n   n
     * |   |
     * n___n
     * |   |
     * n___n
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        if (loop1 == null || loop2 == null) {
            throw new RuntimeException("入环节点为空");
        }
        Node cur1 = head1, cur2 = head2;
        if (loop1 == loop2) {
            // 针对情况二入环节点时同一个，找到其相交节点，采用noLoop中的长链表先走与短链表差值步在一起走比对的方式找出
            int len = 0;
            while (cur1 != loop1) {
                len++;
                cur1 = cur1.next;
            }
            while (cur2 != loop1) {
                len--;
                cur2 = cur2.next;
            }
            cur1 = len > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            len = Math.abs(len);
            while (len > 0) {
                cur1 = cur1.next;
                len--;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            // 从loop1往下走，若与loop2相遇，说明环上相交，返回loop1或loop2均可
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            // 若循环到自己，说明不相交
            return null;
        }
    }

    public static void printVal(Node head) {
        System.out.println(head == null ? "null" : head.val);
    }

}
