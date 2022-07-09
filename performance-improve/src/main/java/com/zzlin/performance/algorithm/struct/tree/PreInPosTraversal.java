package com.zzlin.performance.algorithm.struct.tree;

import java.util.Stack;

/**
 * 树的前中后序遍历
 * 使用递归和非递归两种方式实现，非递归方法面试经常出现
 * 任何递归都可以改成非递归
 * @author zlin
 * @date 20220709
 */
public class PreInPosTraversal {

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 将入参节点放入栈中，若栈不为空，遍历栈：
     *   1、出栈节点
     *   2、处理节点（打印）
     *   3、将节点的右子节点，左子节点依次入栈，如果有的话
     */
    public static void preOrderUnRecur(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            System.out.print(n.val + " ");
            if (n.right != null) {
                stack.push(n.right);
            }
            if (n.left != null) {
                stack.push(n.left);
            }
        }
    }

    public static void preOrderRecur(Node root) {
        // 第一次进入
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        // 第一次进入
        preOrderRecur(root.left);
        // 第二次进入
        preOrderRecur(root.right);
        // 第三次进入
    }

    public static void inOrderRecur(Node root) {
        // 第一次进入
        if (root == null) {
            return;
        }
        // 第一次进入
        inOrderRecur(root.left);
        System.out.print(root.val + " ");
        // 第二次进入
        inOrderRecur(root.right);
        // 第三次进入
    }

    public static void posOrderRecur(Node root) {
        // 第一次进入
        if (root == null) {
            return;
        }
        // 第一次进入
        posOrderRecur(root.left);
        // 第二次进入
        posOrderRecur(root.right);
        System.out.print(root.val + " ");
        // 第三次进入
    }

}
