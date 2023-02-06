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

    public static int findMaximizedCapital(int k, int w, int[] costs, int[] profits) {
        PriorityQueue<Node> minCostQueue = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Node> maxProfitQueue = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < costs.length; i++) {
            minCostQueue.add(new Node(costs[i], profits[i]));
        }
        int result = 0;
        for (int i = 0; i < k; i++) {

        }
        return result;
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
