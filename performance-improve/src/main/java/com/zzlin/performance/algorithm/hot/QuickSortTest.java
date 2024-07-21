package com.zzlin.performance.algorithm.hot;

import java.util.Random;

/**
 * @author pang
 * @date 2024/7/21
 */
public class QuickSortTest {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] res = solution.sortArray(new int[]{5,1,1,2,0,0,-999,5,2,3,1});
        for (int i : res) {
            System.out.print(i + ", ");
        }
    }

    static class Solution {

        private static final Random RANDOM = new Random();

        public int[] sortArray(int[] nums) {
            if (nums.length < 2) {
                return nums;
            }
            quickSort(nums, 0, nums.length - 1);
            return nums;
        }

        private void quickSort(int[] nums, int left, int right) {
            if (left >= right) {
                return;
            }
            swap(nums, left + RANDOM.nextInt(right - left), right);
            int[] p = partation(nums, left, right);
            quickSort(nums, left, p[0] - 1);
            quickSort(nums, p[1] + 1, right);
        }

        private int[] partation(int[] nums, int left, int right) {
            int num = nums[right];
            for (int i = left; i <= right;) {
                if (nums[i] < num) {
                    swap(nums, i++, left++);
                } else if (nums[i] > num) {
                    swap(nums, i, right--);
                } else {
                    i++;
                }
            }
            return new int[]{left, right};
        }

        private void swap(int[] arr, int a, int b) {
            if (a == b) {
                return;
            }
            arr[a] = arr[a] ^ arr[b];
            arr[b] = arr[a] ^ arr[b];
            arr[a] = arr[a] ^ arr[b];
        }

    }
}
