package com.zzlin.performance.algorithm.greedy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入
 * 正数数组costs
 * 正数数组profits
 * 正数k
 * 正m
 * 含义:
 * costs[i]表示i号项目的花费，profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * k表示你只能串行的最多做k个项目，m表示你初始的资金
 * 说明:
 * 你每做完一个项目，马上获得的收益，可以支持你去做下一个项目，项目不可重复做
 * 输出:你最后获得的最大钱数
 *
 * @author zlin
 * @date 20230206
 */
public class IPO {

    /**
     * 你每做完一个项目，马上获得的收益，可以支持你去做下一个项目，项目不可重复做
     *
     * @param k 最多做k个项目
     * @param w 初始的资金
     * @param costs 表示i号项目的花费
     * @param profits 表示i号项目的利润
     * @return 获得的最大钱数
     */
    public static int findMaximizedCapital(int k, int w, int[] costs, int[] profits) {
        // 最小花费的堆
        PriorityQueue<Node> minCostQueue = new PriorityQueue<>(new MinCostComparator());
        // 最大利润的堆
        PriorityQueue<Node> maxProfitQueue = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < costs.length; i++) {
            minCostQueue.add(new Node(costs[i], profits[i]));
        }
        for (int i = 0; i < k; i++) {
            // 当前本金能做哪些项目全部加入到最大利润的堆中
            while (!minCostQueue.isEmpty() && minCostQueue.peek().getCost() <= w) {
                maxProfitQueue.add(minCostQueue.poll());
            }
            // 最大利润的堆为空，说明啥项目都做不了了，直接返回钱数
            if (maxProfitQueue.isEmpty()) {
                return w;
            }
            w += maxProfitQueue.poll().getProfit();
        }
        return w;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Node {
        /**
         * 花费
         */
        private int cost;
        /**
         * 利润
         */
        private int profit;
    }

    private static class MinCostComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.getCost() - o2.getCost();
        }
    }

    private static class MaxProfitComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.getProfit() - o1.getProfit();
        }
    }

}
