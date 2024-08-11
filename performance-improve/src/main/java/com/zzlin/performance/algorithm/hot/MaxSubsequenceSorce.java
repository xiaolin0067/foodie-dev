package com.zzlin.performance.algorithm.hot;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <a href="https://leetcode.cn/problems/maximum-subsequence-score">最大子序列的分数</a>
 *
 * @author pang
 * @date 2024/8/6 01:25
 */
public class MaxSubsequenceSorce {

    public static void main(String[] args) {
        System.out.println(new Solution().maxScore(new int[]{1,3,3,2}, new int[]{2,1,3,4}, 3));
    }

    static class Solution {
        public long maxScore(int[] nums1, int[] nums2, int k) {
            long sum1 = 0; // 使用 long 避免溢出
            Deque<Integer> deque = new ArrayDeque<>();

            // 初始化前 k 个元素
            for (int i = 0; i < k; i++) {
                sum1 += nums1[i];
                // 维护 nums2 的最小值，使用单调递增队列
                while (!deque.isEmpty() && nums2[deque.peekLast()] >= nums2[i]) {
                    deque.pollLast();
                }
                deque.addLast(i);
            }

            // 初始结果
            long res = sum1 * nums2[deque.peekFirst()];

            // 滑动窗口遍历剩余元素
            for (int i = k; i < nums1.length; i++) {
                // 更新 sum1
                sum1 += nums1[i] - nums1[i - k];

                // 维护 nums2 的最小值
                while (!deque.isEmpty() && nums2[deque.peekLast()] >= nums2[i]) {
                    deque.pollLast();
                }
                deque.addLast(i);

                // 移除超出滑动窗口范围的元素
                if (deque.peekFirst() <= i - k) {
                    deque.pollFirst();
                }

                // 计算当前结果
                res = Math.max(res, sum1 * nums2[deque.peekFirst()]);
            }

            return res;
        }
    }


}
