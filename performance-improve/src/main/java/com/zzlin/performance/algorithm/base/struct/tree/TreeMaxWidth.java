package com.zzlin.performance.algorithm.base.struct.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 宽度优先遍历
 * @author zlin
 * @date 20220710
 */
public class TreeMaxWidth {

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
        widthOrderRecur(n1);
        System.out.println();
        System.out.println(getTreeMaxWidthWithMap(n1));
        System.out.println(getTreeMaxWidth(n1));
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
     * 宽度优先遍历：从根节点开始将每一层的从做往右依次打印完
     * 1,2,3,4,5,6,7
     * 将入参节点放入队列中，若队列不为空，遍历队列：
     *   1、节点出队
     *   2、处理节点（打印）
     *   3、将节点的左子节点，右子节点依次入队，如果有的话
     * 每次都将该层的所有节点从左往右全部入队了，后面就是打印，重复操作
     */
    public static void widthOrderRecur(Node root) {
        if (root == null) {
            return;
        }
        System.out.println("宽续遍历：");
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            System.out.print(n.val + ", ");
            // 左边先入队，也就先出来先打印，对子节点重复
            if (n.left != null) {
                queue.add(n.left);
            }
            if (n.right != null) {
                queue.add(n.right);
            }
        }
    }

    /**
     * 获得树的最大宽度，最多节点的一层的节点数量
     * 实现原理：记录每个节点所在的层数。将头节点放入map并记录为第一层，他的则子节点都是第二层，子节点的子节点就是第三层
     * 宽度优先遍历是每次都处理一层的所有节点，即可将该层的节点数累加并将较大着记录为最大长度
     */
    public static int getTreeMaxWidthWithMap(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Map<Node, Integer> map = new HashMap<>();
        map.put(root, 1);
        int level = 1;
        int levelNodeNum = 0;
        int maxLen = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            int nLevel = map.get(n);
            if (nLevel == level) {
                levelNodeNum++;
            }else {
                maxLen = Math.max(levelNodeNum, maxLen);
                level++;
                levelNodeNum = 1;
            }
            if (n.left != null) {
                map.put(n.left, level+1);
                queue.add(n.left);
            }
            if (n.right != null) {
                map.put(n.right, level+1);
                queue.add(n.right);
            }
        }
        return Math.max(levelNodeNum, maxLen);
    }

    /**
     * 获得树的最大宽度，仅使用队列
     * 宽度优先遍历树，记录层的最后一个节点，进入该层对每一个节点计数，走到该层的最后节点时比对并返回
     */
    public static int getTreeMaxWidth(Node root) {
        if (root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        Node curLevelEnd = root;
        Node nextLevelEnd = null;
        int curLevelLen = 0;
        int maxLen = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (n.left != null) {
                // 只要不为空，就设为下一层的最后一个节点，这样该层遍历完后一定能得到最终的下一层的最后节点
                nextLevelEnd = n.left;
                queue.add(n.left);
            }
            if (n.right != null) {
                // 只要不为空，就设为下一层的最后一个节点，这样该层遍历完后一定能得到最终的下一层的最后节点
                nextLevelEnd = n.right;
                queue.add(n.right);
            }
            // 当前层数量累加
            curLevelLen++;
            if (n == curLevelEnd) {
                // 若走到了当前层的最后一个节点，比对当前层的宽度是否是最大的，并重置下一层长度为0，当前层与下一层的最后节点
                maxLen = Math.max(curLevelLen, maxLen);
                curLevelLen = 0;
                curLevelEnd = nextLevelEnd;
                nextLevelEnd = null;
            }
        }
        return maxLen;
    }

}
