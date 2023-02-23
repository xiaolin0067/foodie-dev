package com.zzlin.performance.algorithm.greedy;

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线。
 * 玩家A和玩家B依次拿走每张纸牌，规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A和玩家B都绝顶聪明。
 * 请返回最后获胜者的分数。
 *
 * @author zlin
 * @date 20230223
 */
public class CardInLine {

    public static void main(String[] args) {
        System.out.println(win(new int[]{1,2,100,4}));
    }

    private static int win(int[] arr) {
        return first(arr, 0, arr.length - 1);
    }

    /**
     * 先手者
     *
     * @param arr 原始数组
     * @param l 范围左侧位置
     * @param r 范围右侧位置
     * @return 获得分数
     */
    private static int first(int[] arr, int l, int r) {
        if (l == r) {
            // base case, 先手者走到只有一个数的位置，先手者得分，即为当前位置的分数
            return arr[l];
        }
        // 先手者有两种决策：（先拿左边+右边的后手） 与 （先拿右边+左边的后手）
        // 最为一个绝顶聪明的人，一定是拿二者的较大值
        return Math.max(arr[l] + second(arr, l + 1, r), arr[r] + second(arr, l, r - 1));
    }

    /**
     * 后手者
     *
     * @param arr 原始数组
     * @param l 范围左侧位置
     * @param r 范围右侧位置
     * @return 获得分数
     */
    private static int second(int[] arr, int l, int r) {
        if (l == r) {
            // base case, 后手者走到只有一个数的位置，被先手者得分，后手者不得分返回0
            return 0;
        }
        // 后手者被先手者挑选后只剩两种决策：右边的先手 +与 左边的先手
        // 先手者对后手者的决策的决定是不利的，先手者获得最多，后手者就获得最少，即在两个决策中取较小值
        return Math.min(first(arr, l + 1, r), first(arr, l, r-1));
    }

}
