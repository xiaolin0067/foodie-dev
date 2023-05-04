package com.zzlin.performance.algorithm.improvement.dp.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二叉树节点间的最大距离问题
 * 从二叉树的节点a出发，可以向上或者向下走，但沿途的节点只能经过一次，到达节点b时路径上的节点个数叫作a到b的距离。
 * 那么二叉树任何两个节点之间都有距离，求整棵树上的最大距离。
 *
 * 分析：
 * 过X节点的最大距离有三种情况：
 * 1、最远距离为左子树上的最大距离
 * 2、最远距离为右子树上的最大距离
 * 3、最远距离为：左子树的高度+右子树的高度+1
 * 所以可以设计从左右子树获取两个信息来实现：高度，最远距离
 *
 * @author zlin
 * @date 20230430
 */
public class MaxDistanceTree {

    public static int getMaxDistance(Node node) {
        return process(node).getMaxDistance();
    }

    private static TreeInfo process(Node node) {
        if (node == null) {
            return new TreeInfo(0, 0);
        }
        TreeInfo leftTreeInfo = process(node.getLeft());
        TreeInfo rightTreeInfo = process(node.getRight());
        int leftMax = leftTreeInfo.getMaxDistance();
        int rightMax = rightTreeInfo.getMaxDistance();
        int xMax = leftTreeInfo.getHeight() + rightTreeInfo.getMaxDistance() + 1;
        int maxDistance = Math.max(Math.max(leftMax, rightMax), xMax);
        int nHeight = Math.max(leftTreeInfo.getHeight(), rightTreeInfo.getHeight()) + 1;
        return new TreeInfo(maxDistance, nHeight);
    }

    @Data
    static class Node {
        private int value;
        private Node left;
        private Node right;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TreeInfo {
        /**
         * 最远距离
         */
        private int maxDistance;
        /**
         * 高度
         */
        private int height;

    }

}
