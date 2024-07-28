package com.zzlin.performance.algorithm.hot;

import java.util.Arrays;

/**
 * 最长递增子序列
 *
 * @author pang
 * @date 2024/7/28 18:55
 */
public class LongestIncreaseSubSequence {

    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}) == 4);
        System.out.println(lengthOfLIS(new int[]{0,1,0,3,2,3}) == 4);
        System.out.println(lengthOfLIS(new int[]{7,7,7,7,7,7,7}) == 1);
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] >= nums[i]) {
                    continue;
                }
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

}
