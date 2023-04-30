package com.zzlin.performance.algorithm.improvement.tree.dp;

import lombok.Data;

/**
 * Morris遍历细节
 * 假设来到当前节点cur，开始时cur来到头节点位置
 * 1)如果cur没有左孩子，cur向右移动(cur = cur.right)
 * 2)如果cur有左孩子，找到左子树上最右的节点mostRight
 *     a.如果mostRight的右指针指向空，让其指向cur然后cur向左移动(cur = cur.left)
 *     b.如果mostRight的右指针指向cur，让其指向null然后cur向右移动(cur = cur.right)
 * 3)cur为空时遍历停止
 *
 * 只有有左子树的节点会被遍历两次，并且可以知道是第几次遍历：
 * 1)若cur左子树上最右的节点mostRight如果是指向空，即第一次遍历
 * 2)若cur左子树上最右的节点mostRight如果是指向cur，即第二次遍历
 *
 * @author zlin
 * @date 20230430
 */
public class MorrisTraversal {


    @Data
    static class Node {
        private int value;
        private Node left;
        private Node right;
    }

    private static void process(Node node) {
        if (node == null) {
            return;
        }
        // 1
        process(node.getLeft());
        // 2
        process(node.getRight());
        // 3
    }

    public static void morris(Node node) {
        if (node == null) {
            return;
        }
        Node mostRight = node;

    }

}
