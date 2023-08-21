package com.zzlin.performance.algorithm.base.struct.tree;

import java.util.Stack;

/**
 * 是否为二叉搜索树
 *   所有节点值不重复，左子节点比他小，右子节点比他大
 * @author zlin
 * @date 20220712
 */
public class CheckBST {

    public static void main(String[] args) {
        Node n5 = new Node(5);
        Node n3 = new Node(3);
        Node n7 = new Node(7);
        Node n2 = new Node(2);
        Node n4 = new Node(4);
        Node n6 = new Node(6);
        Node n8 = new Node(8);
        n5.left = n3;
        n5.right = n7;
        n3.left = n2;
//        n3.right = n4;
        n7.left = n6;
//        n7.right = n8;
        System.out.println(checkBstRecur(n5));
        System.out.println(checkBstUnRecur(n5));
        System.out.println(checkBst(n5));
        unBst();
    }

    private static void unBst() {
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
        System.out.println(checkBstRecur(n1));
        System.out.println(checkBstUnRecur(n1));
        System.out.println(checkBst(n1));
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
     * 中序遍历，若结果是升序则为二叉搜索树
     */
    private static int preVal = Integer.MIN_VALUE;
    public static boolean checkBstRecur(Node root) {
        if (root == null) {
            return true;
        }
        boolean checkLeft = checkBstRecur(root.left);
        if (!checkLeft || root.val <= preVal) {
            return false;
        }else {
            preVal = root.val;
        }
        return checkBstRecur(root.right);
    }

    /**
     * 中序遍历，若结果是升序则为二叉搜索树
     */
    public static boolean checkBstUnRecur(Node root) {
        if (root == null) {
            return true;
        }
        int preVal = Integer.MIN_VALUE;
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            }else {
                root = stack.pop();
                if (root.val <= preVal) {
                    return false;
                }
                preVal = root.val;
                root = root.right;
            }
        }
        return true;
    }

    private static class Info {
        public boolean isBst;
        public Integer maxVal;
        public Integer minVal;

        public Info(boolean isBst, Integer maxVal, Integer minVal) {
            this.isBst = isBst;
            this.maxVal = maxVal;
            this.minVal = minVal;
        }
    }

    /**
     * 分治套路解决二叉搜索树问题-入口方法
     */
    public static boolean checkBst(Node root) {
        return process(root).isBst;
    }

    /**
     * 分治套路解决二叉搜索树问题
     * 获取子树是否是二叉搜索树、最大值、最小值，组装当前节点的信息
     */
    private static Info process(Node root) {
        if (root == null) {
            return new Info(true, null, null);
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        return new Info(
                // 左右子树都是搜索二叉树，并且当前节点大于左子树的最大值，小于右子树的最小值
                leftInfo.isBst &&
                      rightInfo.isBst &&
                      (leftInfo.maxVal == null || root.val > leftInfo.maxVal) &&
                      (rightInfo.minVal == null || root.val < rightInfo.minVal),
                // 左子树的最小值不为空，即为最小值，否则为当前节点值
                leftInfo.minVal == null ? root.val : leftInfo.minVal,
                // 右子树的最大值不为空，即为最大值，否则为当前节点值
                rightInfo.maxVal == null ? root.val : rightInfo.maxVal
        );
    }

}
