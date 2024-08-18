package com.zzlin.performance.algorithm.hot.array;

/**
 * 递增的三元子序列
 * <a href="https://leetcode.cn/problems/increasing-triplet-subsequence"></a>
 *
 * @author pang
 * @date 2024/8/18 16:49
 * @since V3.17.0
 */
public class IncreasingTripletSubsequence {

    public static void main(String[] args) {
        System.out.println(new Solution().increasingTriplet(new int[]{1,2,3,4,5}));
    }

    static class Solution {
        public boolean increasingTriplet(int[] nums) {
            if (nums.length < 3) {
                return false;
            }
            int min = nums[0];
            int mid = Integer.MAX_VALUE;
            for (int i = 1; i < nums.length; i++) {
                if (mid > min && nums[i] > mid) {
                    return true;
                } else if (nums[i] < min) {
                    min = nums[i];
                } else if (nums[i] > min && mid > nums[i]) {
                    mid = nums[i];
                }
            }
            return false;
        }
    }
}
