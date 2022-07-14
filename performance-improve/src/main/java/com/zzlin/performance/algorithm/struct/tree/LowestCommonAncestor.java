package com.zzlin.performance.algorithm.struct.tree;

/**
 * 给点一个二叉树中的两个节点node1, node2，找到他们的最低公共祖先 leetcode 236
 * @author zlin
 * @date 20220715
 */
public class LowestCommonAncestor {

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 寻找树中两个节点的最低公共祖先，需要保证两个节点一定在树中
     * o1，o2在树中只有两种情况
     *   1、o1为o2的最低公共祖先或o2为o1的最低公共祖先
     *   2、o1与o2不为对方的最低公共祖先，需要往上找
     *         o
     *       /  \
     *     o1    o
     *   /  \   / \
     *  o   o2 o   o
     */
    public static Node getLowestCommonAncestor(Node head, Node o1, Node o2) {
        // 找到了o1或者o2直接返回
        if (head == null || head == o1 || head == o2) {
            return head;
        }
        Node leftAncestor = getLowestCommonAncestor(head.left, o1, o2);
        Node rightAncestor = getLowestCommonAncestor(head.right, o1, o2);
        // 情况1不可能左右都不为空，因为其他代码就是找子树中的o1或o2的节点，若子树中没有o1或o2的一侧的树必定返回null
        // 情况2左右子树都不为空了，说明当前节点是最小父节点，因为没有o1,o2的一侧必定返回null，所以能将当前节点返回
        if (leftAncestor != null && rightAncestor != null) {
            return head;
        }
        // 返回非null节点
        return leftAncestor == null ? rightAncestor : leftAncestor;
    }

}
