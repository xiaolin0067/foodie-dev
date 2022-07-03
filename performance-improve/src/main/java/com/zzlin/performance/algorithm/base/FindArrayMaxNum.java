package com.zzlin.performance.algorithm.base;

/**
 * 二分查找
 * @author zlin
 * @date 20220605
 */
public class FindArrayMaxNum {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        System.out.println(findMax(arr));
    }

    public static int findMax(int[] arr) {
        return findMax(arr, 0, arr.length - 1);
    }

    /**
     * T(N) = 2 * T(N/2) + O(1)
     * log(b,a)=1 = d=1 -> 复杂度为O(N ^ log(b,a)) -> O(N)
     */
    public static int findMax(int[] arr, int left, int right) {
        if (left > right) {
            throw new RuntimeException("参数错误");
        }
        if (left == right) {
            return arr[left];
        }
        // 取中点若采用常规方式 (left + right) / 2 可能会有问题，因为left + right可能会超过最大值，导致变为负数
        // 且算数运算速度较慢
        // 这里采用: 中点 = left + (right - left) / 2, /2采用位运算向右移动一位
        int mid = left + ((right - left) >> 1);
        int leftMax = findMax(arr, left, mid);
        int rightMax = findMax(arr, mid + 1, right);
        // 这一步是O(1)
        return Math.max(leftMax, rightMax);
    }

}
