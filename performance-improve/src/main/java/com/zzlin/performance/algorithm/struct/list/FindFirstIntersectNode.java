package com.zzlin.performance.algorithm.struct.list;

/**
 * @author zlin
 * @date 20220707
 */
public class FindFirstIntersectNode {

    public static void main(String[] args) {
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
     * 若两个链表都不是环，遍历两个链表到结束，记录两个链表的长度和尾节点
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

}
