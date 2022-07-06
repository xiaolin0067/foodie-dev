package com.zzlin.performance.algorithm.struct.list;

/**
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

    public static Node smallerEqualBigger(Node head, int pivot) {
        if (head == null) {
            return null;
        }
        Node sh = null, st = null, eh = null, et = null, bh = null, bt = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.val < pivot) {
                if (sh == null) {
                    sh = st = head;
                }else {
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
