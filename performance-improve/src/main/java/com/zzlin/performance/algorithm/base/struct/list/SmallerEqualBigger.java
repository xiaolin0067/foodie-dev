package com.zzlin.performance.algorithm.base.struct.list;

/**
 * 单链表荷兰国旗问题
 * @author zlin
 * @date 20220707
 */
public class SmallerEqualBigger {

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
        Node n = smallerEqualBigger(node1, 2);
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
     * 把链表划分为小于范围，等于范围，大于范围
     * 只用有限个空间
     * 空间复杂度O(1)
     */
    public static Node smallerEqualBigger(Node head, int pivot) {
        if (head == null) {
            return null;
        }
        Node sh = null, st = null, eh = null, et = null, bh = null, bt = null, next;
        while (head != null) {
            // 记录下一个节点
            next = head.next;
            // 把下一个节点置为null，否则会成环
            head.next = null;
            if (head.val < pivot) {
                if (sh == null) {
                    // 若开始范围为空，则开始结束都为空，都置为当前节点
                    sh = st = head;
                }else {
                    // 否则把结束范围节点只想当前节点，并把结束范围移向当前节点
                    st.next = head;
                    st = head;
                }
            }else if (head.val > pivot) {
                if (bh == null) {
                    bh = bt = head;
                }else {
                    bt.next = head;
                    bt = head;
                }
            }else {
                if (eh == null) {
                    eh = et = head;
                }else {
                    et.next = head;
                    et = head;
                }
            }
            head = next;
        }
        // 需要把三段中间的两处连接起来：st -> eh, et -> bh
        if (st != null) {
            st.next = eh;
            // 若eh为空，则需要用st去连bh了
            et = et == null ? st : et;
        }
        if (et != null) {
            et.next = bh;
        }
        return sh != null ? sh : (eh != null ? eh : bh);
    }

}
