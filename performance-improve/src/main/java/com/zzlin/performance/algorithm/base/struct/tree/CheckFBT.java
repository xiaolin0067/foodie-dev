package com.zzlin.performance.algorithm.base.struct.tree;

/**
 * 是否为满二叉树
 * 节点数与层级满足
 *   N = (2 ^ L) -1
 *   N：节点数，L：树的深度，层数
 * @author zlin
 * @date 20220714
 */
public class CheckFBT {

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
        System.out.println(checkFBT(n1));
        n7.right = new Node(8);
        System.out.println(checkFBT(n1));
        n3.right = null;
        System.out.println(checkFBT(n1));
        n3.left = null;
        System.out.println(checkFBT(n1));
        n2.right = null;
        n2.left = null;
        n3.right = null;
        n3.left = null;
        System.out.println(checkFBT(n1));
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    public static class ResultData {
        public int nodeNum;
        public int levelNum;

        public ResultData(int nodeNum, int levelNum) {
            this.nodeNum = nodeNum;
            this.levelNum = levelNum;
        }
    }

    public static boolean checkFBT(Node root) {
        if (root == null) {
            return false;
        }
        ResultData resultData = process(root);
        return resultData.nodeNum == ((1 << resultData.levelNum) - 1);
    }

    public static ResultData process(Node root) {
        if (root == null) {
            return new ResultData(0,0);
        }
        ResultData leftData = process(root.left);
        ResultData rightData = process(root.right);
        return new ResultData(
                leftData.nodeNum + rightData.nodeNum + 1,
                Math.max(leftData.levelNum, rightData.levelNum) + 1
        );
    }

}
