package com.zzlin.performance.algorithm.struct.tree;

/**
 * 是否为满二叉树
 * @author zlin
 * @date 20220714
 */
public class CheckFBT {

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
        return resultData.nodeNum == (1 << resultData.levelNum - 1);
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
