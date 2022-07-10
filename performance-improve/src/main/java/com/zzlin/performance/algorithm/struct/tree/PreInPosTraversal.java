package com.zzlin.performance.algorithm.struct.tree;

import java.util.Stack;

/**
 * 树的前中后序遍历
 * 使用递归和非递归两种方式实现，非递归方法面试经常出现
 * 任何递归都可以改成非递归
 * 先序遍历：头左右。
 * 中序遍历：左头右。
 * 后序遍历：左右头。
 * @author zlin
 * @date 20220709
 */
public class PreInPosTraversal {

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
        preOrderRecur(n1);
        System.out.println();
        preOrderUnRecur(n1);
        System.out.println();
        inOrderRecur(n1);
        System.out.println();
        inOrderUnRecur(n1);
        System.out.println();
        posOrderRecur(n1);
        System.out.println();
        posOrderUnRecur(n1);
    }


    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 不递归先序遍历
     * 将入参节点放入栈中，若栈不为空，遍历栈：
     *   1、节点出栈
     *   2、处理节点（打印）
     *   3、将节点的右子节点，左子节点依次入栈，如果有的话
     * 先入栈右子节点，出栈顺序：头左右
     */
    public static void preOrderUnRecur(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("不递归先序遍历：");
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

    /**
     * 不递归后序遍历
     * 将入参节点放入栈中，若栈不为空，遍历栈：
     *   1、节点出栈，放入另外一个收集栈
     *   2、将节点的左子节点，右子节点依次入栈，如果有的话
     * 先入栈左子节点，出栈顺序：头右左
     * 收集栈出栈顺序与之相反：左右头
     * 遍历完后，将收集栈所有节点出栈
     */
    public static void posOrderUnRecur(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("不递归后续遍历：");
        Stack<Node> stack = new Stack<>();
        Stack<Node> collect = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            collect.push(n);
            if (n.left != null) {
                stack.push(n.left);
            }
            if (n.right != null) {
                stack.push(n.right);
            }
        }
        while (!collect.isEmpty()) {
            System.out.print(collect.pop().val + ", ");
        }
    }

    /**
     * 不递归中序遍历
     * 将入参节点左边界节点放入栈中，若栈不为空，遍历栈：
     *   1、节点出栈
     *   2、处理节点（打印）
     *   3、将节点的右子节点的左边界节点入栈，如果有的话
     */
    public static void inOrderUnRecur(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("不递归中续遍历：");
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                // 把当前节点的左边界节点都入栈
                stack.push(root);
                root = root.left;
            } else {
                // 根据while条件，root为空，栈一定不为空
                // 左边界没节点了，弹出节点并打印，将当前节点设为右子节点，重复此操作
                root = stack.pop();
                System.out.print(root.val + ", ");
                root = root.right;
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
