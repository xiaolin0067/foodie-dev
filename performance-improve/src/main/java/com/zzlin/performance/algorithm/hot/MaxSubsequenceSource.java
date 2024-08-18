package com.zzlin.performance.algorithm.hot;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <a href="https://leetcode.cn/problems/maximum-subsequence-score">最大子序列的分数</a>
 *
 * @author pang
 * @date 2024/8/6 01:25
 */
public class MaxSubsequenceSource {

    public static void main(String[] args) {
        System.out.println(new Solution().maxScore(new int[]{1,3,3,2}, new int[]{2,1,3,4}, 3));
    }

    static class Solution {
        public long maxScore(int[] nums1, int[] nums2, int k) {
            // 存放 nums2 中元素倒序排列的index
            Integer[] nums2DescIdx = new Integer[nums2.length];
            for (int i = 0; i < nums2.length; i++) {
                nums2DescIdx[i] = i;
            }
            // 根据 nums2 中元素倒序排列的index
            Arrays.sort(nums2DescIdx, (a, b) -> Integer.compare(nums2[b], nums2[a]));
            // 存放当前sum1的所有元素
            PriorityQueue<Integer> nums1ElementPq = new PriorityQueue<>();
            int kIdx = k - 1;
            long sum1 = 0;
            for (int i = 0; i < kIdx; i++) {
                sum1 += nums1[nums2DescIdx[i]];
                nums1ElementPq.add(nums1[nums2DescIdx[i]]);
            }
            long res = Long.MIN_VALUE;;
            for (int i = kIdx; i < nums2.length; i++) {
                // sum1是从nums2DescIdx中的位置中选择的，所以子序列的位置是一致的
                sum1 += nums1[nums2DescIdx[i]];
                nums1ElementPq.add(nums1[nums2DescIdx[i]]);
                // 当前的nums2是当前位置最小的那个了，因为nums2DescIdx是根据值倒序排列的
                res = Math.max(res, sum1 * nums2[nums2DescIdx[i]]);
                // 弹出sum1所有元素中最小的那个，以始终保持sum1只有k的元素
                sum1 -= nums1ElementPq.poll();
            }
            return res;
        }
    }


}
