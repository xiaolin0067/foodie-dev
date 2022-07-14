package com.zzlin.performance.algorithm.struct.tree;

/**
 * 检查是否是平衡二叉树
 * 左子树是平衡二叉树，右子树是平衡二叉树，左右子树高度差不超过1
 *   从左子树中获取信息：是否是平衡二叉树、高度
 *   从右子树中获取信息：是否是平衡二叉树、高度
 * @author zlin
 * @date 20220714
 */
public class CheckBBT {

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
        System.out.println(checkBBT(n1));

        n1.right = null;
        System.out.println(checkBBT(n1));
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    private static class Info {
        public boolean isBbt;
        public int height;

        public Info(boolean isBbt, int height) {
            this.isBbt = isBbt;
            this.height = height;
        }
    }

    /**
     * 节点是否是平衡二叉树
     * 入口方法
     */
    public static boolean checkBBT(Node root) {
        return process(root).isBbt;
    }

    /**
     * 节点是否是平衡二叉树
     * 采用动态规划，将左右子树的结果信息合并为当前节点的结果
     */
    private static Info process(Node root) {
        if (root == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        return new Info(
                // 左右子树为平衡树，且左右子树高度差不超过1
                leftInfo.isBbt && rightInfo.isBbt && (Math.abs(leftInfo.height - rightInfo.height) <= 1),
                // 新高度=较高的子树高度+1
                Math.max(leftInfo.height, rightInfo.height) + 1);
    }


}
