package com.zzlin.performance.algorithm.struct.tree;

/**
 * 折纸问题
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。
 * 此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。请从上到下打印所有折痕的方向。
 * 例如:N=1时，打印: down N=2时，打印: down down up
 *
 * 其结果是一个一凹为根节点，左子节点都是凹，右子节点都是凸的数的中序遍历的结果
 *            凹
 *          /   \
 *         凹    凸
 *        / \   / \
 *       凹  凸 凹  凸
 * 中序遍历结果：凹凹凸凹凹凸凸
 * @author zlin
 * @date 20220716
 */
public class PaperFolding {

    public static void main(String[] args) {
        printAllFolds(1);
        printAllFolds(2);
        printAllFolds(3);
        printAllFolds(4);
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    public static void printAllFolds(int n) {
        if (n < 1) {
            return;
        }
        System.out.println("\r\n折纸问题，" + n + "层：");
        printProcess(1, n, true);
    }

    private static void printProcess(int i, int n, boolean b) {
        if (i > n) {
            return;
        }
        printProcess(i + 1, n, true);
        System.out.print(b ? "凹" : "凸");
        printProcess(i + 1, n, false);
    }

}
