package com.zzlin.performance.algorithm.improvement.dp.tree;

import lombok.Data;

/**
 * Morris遍历细节
 * 时间复杂度O(N)，空间复杂度O(1)
 * 假设来到当前节点cur，开始时cur来到头节点位置
 * 1)如果cur没有左孩子，cur向右移动(cur = cur.right)
 * 2)如果cur有左孩子，找到左子树上最右的节点mostRight
 *   a.如果mostRight的右指针指向空，让其指向cur然后cur向左移动(cur = cur.left)
 *   b.如果mostRight的右指针指向cur，让其指向null然后cur向右移动(cur = cur.right)
 * 3)cur为空时遍历停止
 * <p>
 * 只有有左子树的节点会被遍历两次，并且可以知道是第几次遍历：
 *   1)若cur左子树上最右的节点mostRight如果是指向空，即第一次遍历
 *   2)若cur左子树上最右的节点mostRight如果是指向cur，即第二次遍历
 *
 * @author zlin
 * @date 20230430
 */
public class MorrisTraversal {

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
        morrisPre(n1);
        System.out.println();
        morrisIn(n1);
        System.out.println();
        morrisPost(n1);
    }


    @Data
    static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
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
        Node cur = node;
        Node mostRight;
        // 3)cur为空时遍历停止
        while (cur != null) {
            // 1)如果cur没有左孩子，cur向右移动
            if (cur.getLeft() == null) {
                cur = cur.getRight();
                continue;
            }
            // 2)如果cur有左孩子(有左子树的节点会被遍历两次)，找到左子树上最右的节点mostRight
            mostRight = cur.getLeft();
            while (mostRight.getRight() != null && mostRight.getRight() != cur) {
                mostRight = mostRight.getRight();
            }
            if (mostRight.getRight() == null) {
                // a.如果mostRight的右指针指向空，让其指向cur然后cur向左移动
                // 1)cur左子树上最右的节点mostRight如果是指向空，即第一次遍历
                mostRight.setRight(cur);
                cur = cur.getLeft();
            } else {
                // b.如果mostRight的右指针指向cur，让其指向null然后cur向右移动
                // 2)cur左子树上最右的节点mostRight如果是指向cur，即第二次遍历
                mostRight.setRight(null);
                cur = cur.getRight();
            }
        }
    }

    /**
     * morris先序遍历
     * <p>
     * 节点只会被遍历一次的：直接打印(处理)
     * 节点只会被遍历俩次的：只打印(处理)第一次的遍历
     * <p>
     * 节点只会被遍历一次的：直接打印(处理)
     * 节点只会被遍历俩次的：只打印(处理)第二次的遍历
     *
     * @param node 待遍历的二叉树
     */
    public static void morrisPre(Node node) {
        if (node == null) {
            return;
        }
        Node cur = node;
        Node mostRight;
        while (cur != null) {
            if (cur.getLeft() == null) {
                // 先序遍历
                System.out.print(cur.getValue() + " ");
                cur = cur.getRight();
                continue;
            }
            mostRight = cur.getLeft();
            while (mostRight.getRight() != null && mostRight.getRight() != cur) {
                mostRight = mostRight.getRight();
            }
            if (mostRight.getRight() == null) {
                // 先序遍历
                System.out.print(cur.getValue() + " ");
                mostRight.setRight(cur);
                cur = cur.getLeft();
            } else {
                mostRight.setRight(null);
                cur = cur.getRight();
            }
        }
    }

    /**
     * morris中序遍历
     * <p>
     * 节点只会被遍历一次的：直接打印(处理)
     * 节点只会被遍历俩次的：只打印(处理)第二次的遍历
     *
     * @param node 待遍历的二叉树
     */
    public static void morrisIn(Node node) {
        if (node == null) {
            return;
        }
        Node cur = node;
        Node mostRight;
        while (cur != null) {
            if (cur.getLeft() == null) {
                // 中序遍历
                System.out.print(cur.getValue() + " ");
                cur = cur.getRight();
                continue;
            }
            mostRight = cur.getLeft();
            while (mostRight.getRight() != null && mostRight.getRight() != cur) {
                mostRight = mostRight.getRight();
            }
            if (mostRight.getRight() == null) {
                mostRight.setRight(cur);
                cur = cur.getLeft();
            } else {
                // 中序遍历
                System.out.print(cur.getValue() + " ");
                mostRight.setRight(null);
                cur = cur.getRight();
            }
        }
    }

    /**
     * morris后序遍历
     * <p>
     * 节点只会被遍历俩次的：逆序打印(处理)左子树的右边界
     * 最后整颗树逆序打印(处理)右边界
     *
     * @param node 待遍历的二叉树
     */
    public static void morrisPost(Node node) {
        if (node == null) {
            return;
        }
        Node cur = node;
        Node mostRight;
        while (cur != null) {
            if (cur.getLeft() == null) {
                cur = cur.getRight();
                continue;
            }
            mostRight = cur.getLeft();
            while (mostRight.getRight() != null && mostRight.getRight() != cur) {
                mostRight = mostRight.getRight();
            }
            if (mostRight.getRight() == null) {
                mostRight.setRight(cur);
                cur = cur.getLeft();
            } else {
                mostRight.setRight(null);
                // 后续遍历
                printReverseRightEdge(cur.getLeft());
                cur = cur.getRight();
            }
        }
        // 后续遍历
        printReverseRightEdge(node);
    }

    private static void printReverseRightEdge(Node node) {
        Node tail = reverseRightEdge(node);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.getValue() + " ");
            cur = cur.getRight();
        }
        reverseRightEdge(tail);
    }

    private static Node reverseRightEdge(Node from) {
        Node pre = null;
        Node next;
        while (from != null) {
            next = from.getRight();
            from.setRight(pre);
            pre = from;
            from = next;
        }
        return pre;
    }

    /**
     * 使用morris遍历判断树是否为二叉搜索树
     * 判断二叉搜索树：中序遍历结果升序
     * @param node 树的头结点
     * @return 是否为二叉搜索树
     */
    public static boolean morrisBST(Node node) {
        if (node == null) {
            return false;
        }
        Node cur = node;
        Node mostRight;
        // 上一个中序遍历的值
        int preInValue = Integer.MIN_VALUE;
        while (cur != null) {
            mostRight = cur.getLeft();
            if (mostRight != null) {
                while (mostRight.getRight() != null && mostRight.getRight() != cur) {
                    mostRight = mostRight.getRight();
                }
                if (mostRight.getRight() == null) {
                    mostRight.setRight(cur);
                    cur = cur.getLeft();
                    continue;
                } else {
                    mostRight.setRight(null);
                }
            }
            // 中序遍历
            if (cur.getValue() < preInValue) {
                return false;
            }
            preInValue = cur.getValue();
            cur = cur.getRight();
        }
        return true;
    }

}
