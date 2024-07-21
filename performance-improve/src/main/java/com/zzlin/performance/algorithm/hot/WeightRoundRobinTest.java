package com.zzlin.performance.algorithm.hot;

import java.util.Random;

/**
 * 加权轮询算法 wrr Weighted Round Robin
 * <a href="https://leetcode.cn/problems/cuyjEf/">Leet Code 071:按权重随机选择</a>
 *
 * @author pang
 * @date 2024/7/21
 */
public class WeightRoundRobinTest {

    public static void main(String[] args) {

        Solution solution = new Solution(new int[]{1,3});

        for (int i = 0; i < 10; i++) {
            System.out.println(solution.pickIndex());
        }
    }

    static class Solution {

        private int total;
        /**
         * 前缀和
         */
        private final int[] preSumArr;
        private final Random random;

        public Solution(int[] w) {
            this.random = new Random();
            this.preSumArr = new int[w.length];
            for (int i = 0; i < w.length; i++) {
                total += w[i];
                preSumArr[i] = (i > 0 ? preSumArr[i - 1] : 0) + w[i];
            }
        }

        public int pickIndex() {
            // [0, total - 1) ->  [1, total]
            int hit = random.nextInt(total) + 1;
            int left = 0;
            int right = preSumArr.length - 1;
            while (left < right) {
                int mid = left + ((right - left) >> 1);
                if (hit > preSumArr[mid]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }
    }
}
