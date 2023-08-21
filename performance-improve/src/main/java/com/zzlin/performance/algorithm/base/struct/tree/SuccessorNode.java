package com.zzlin.performance.algorithm.base.struct.tree;

/**
 * 找到节点的后继节点
 * 原始方式：中序遍历树，结果列表在节点后面的节点就是后继节点, O(N)
 * 若能提供每个节点的父节点的正确的指针，可减少遍历次数
 * @author zlin
 * @date 20220715
 */
public class SuccessorNode {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;

        n2.parent = n1;
        n3.parent = n1;
        n4.parent = n2;
        n5.parent = n2;
        n6.parent = n3;
        n7.parent = n3;

        Node successorNode = getSuccessorNode(n7);
        System.out.println(successorNode == null ? null : successorNode.val);

    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 通过有父节点指针，则可减少对树的遍历次数。
     *   若X有右树，后继节点为右树的最左节点
     *   若X无右树，X向上判断是否为其父节点的左孩子：若是父节点的左孩子，父节点即后继节点。若找到根节点仍不是，则后继节点为null。
     */
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            // 若X有右树，后继节点为右树的最左节点
            Node n = node.right;
            while (n.left != null) {
                n = n.left;
            }
            return n;
        } else {
            // 若X无右树，X向上判断是否为其父节点的左孩子：若是父节点的左孩子，父节点即后继节点。若找到根节点仍不是，则后继节点为null。
            Node parent = node.parent;
            while (parent != null && node != parent.left) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

}
