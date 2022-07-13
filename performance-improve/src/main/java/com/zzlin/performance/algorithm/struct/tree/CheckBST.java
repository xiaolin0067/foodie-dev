package com.zzlin.performance.algorithm.struct.tree;

import java.util.Stack;

/**
 * 是否为二叉搜索树
 *   所有节点值不重复，左子节点比他小，右子节点比他大
 * @author zlin
 * @date 20220712
 */
public class CheckBST {

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
                root = root.right;
            }
        }
        return true;
    }


}
