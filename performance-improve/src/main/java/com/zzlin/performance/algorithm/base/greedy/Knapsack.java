package com.zzlin.performance.algorithm.base.greedy;

/**
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。返回你能装下最多的价值是多少?
 *
 * @author zlin
 * @date 20230225
 */
public class Knapsack {

    private static int maxValue(int[] weights, int[] values, int bag) {
        return process(weights, values, bag, 0, 0);
    }

    /**
     * @param weights 不变参数，i位置货物重量
     * @param values 不变参数，i位置货物价值
     * @param bagCapacity 不变参数，袋子能装的货物重量
     * @param bagWeight 变参，袋子当前装了多重的货物
     * @param i 当前货物位置
     * @return 袋子能装的最大货物价值
     */
    private static int process(int[] weights, int[] values, int bagCapacity, int bagWeight, int i) {
        if (i == weights.length || bagWeight > bagCapacity) {
            // base case, 已经来到了末尾位置，i位置到达末尾不能增加价值
            // 袋子已超重, 不能装下任何价值，这个方案不应该有，获得价值为0
            return 0;
        }
        // 未到最终位置且袋子能装下当前货物
        return Math.max(
                // 要当前货物
                values[i] + process(weights, values, bagCapacity, bagCapacity + weights[i], i + 1),
                // 不要当前货物
                process(weights, values, bagCapacity, bagCapacity, i + 1)
                );
    }

    private static int maxValue2(int[] weights, int[] values, int bag) {
        int[][] dp = new int[weights.length][bag + 1];
        for (int i = weights.length - 1; i >= 0; i--) {
            for (int j = bag; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j];
                if (j + weights[i] < bag) {
                    dp[i][j] = Math.max(dp[i][j], values[i] + dp[i + 1][j + values[i]]);
                }
            }
        }
        return dp[0][0];
    }


}
