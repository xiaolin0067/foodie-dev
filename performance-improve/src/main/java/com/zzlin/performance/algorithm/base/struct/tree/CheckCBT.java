package com.zzlin.performance.algorithm.base.struct.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 如何判断一颗二叉树是完全二叉树?
 *   树的每一层都是满的，若不满也是最后一层从左往右是满的，后面缺失几个
 *   宽度优先遍历，判断条件：
 *     1、任一节点有右无左，即非完全二叉树
 *     2、在条件1下，遇到的第一个子节点不全的的节点，其后续节点应该全是叶节点，否则非完全二叉树
 * @author zlin
 * @date 20220712
 */
public class CheckCBT {

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
        System.out.println(checkCBT(n1));
        n3.left = null;
        System.out.println(checkCBT(n1));
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    public static boolean checkCBT(Node head) {
        if (head == null) {
            return false;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        boolean shouldBeLeaf = false;
        Node left, right;
        while (!queue.isEmpty()) {
            head = queue.poll();
            left = head.left;
            right = head.right;
            if (
                    // 1、右节点不为空而左节点为空
                    (right != null && left == null)
                            ||
                    // 2、应该是叶子节点但却不是
                    (shouldBeLeaf && (left != null || right != null))) {
                return false;
            }
            if (left!= null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            // 有为空的而不满足非完全二叉树的条件，说明是第一个叶子节点不全的节点，后面的都应该是叶子节点了
            if (left == null || right == null) {
                shouldBeLeaf = true;
            }
        }
        return true;
    }

}
