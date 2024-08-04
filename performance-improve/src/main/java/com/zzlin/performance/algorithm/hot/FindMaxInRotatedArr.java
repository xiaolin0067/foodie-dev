package com.zzlin.performance.algorithm.hot;

/**
 * 在旋转后的有序数组中找最大值
 *
 * @author pang
 * @date 2024/8/3 23:11
 */
public class FindMaxInRotatedArr {

    public static void main(String[] args) {
        System.out.println(new Solution().search(new int[]{1}, 0));
    }

    /**
     * 有序数组旋转过k个元素，使用log(n)的时间复杂度找最大值
     * <a href="https://leetcode.cn/problems/search-in-rotated-sorted-array/">搜索旋转排序数组</a>
     */
    static class Solution {
        public int search(int[] nums, int target) {
            if (nums.length < 2) {
                return nums[0] == target ? 0 : -1;
            }
            int left = 0;
            int right = nums.length - 1;
            if (nums[left] == target) {
                return left;
            }
            if (nums[right] == target) {
                return right;
            }
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (nums[mid] == target) {
                    return mid;
                }
                if (nums[0] <= nums[mid]) {
                    if (nums[0] < target && target < nums[mid]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                } else {
                    if (nums[mid] < target && target < nums[nums.length - 1]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
            return -1;
        }
    }

}
