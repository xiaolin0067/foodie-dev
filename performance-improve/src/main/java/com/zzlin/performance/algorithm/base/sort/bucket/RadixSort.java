package com.zzlin.performance.algorithm.base.sort.bucket;

/**
 * 非比较排序-基数排序
 * @author zlin
 * @date 20220703
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = new int[]{13,21,11,52,62};
        radixSort(arr);
        for (int value : arr) {
            System.out.print(value + ", ");
        }
    }

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0,  arr.length-1, maxBits(arr));
    }

    /**
     * 基数排序
     * @param arr 待排数组
     * @param left 需要排序的左侧开始位置
     * @param right 需要排序的右侧结束位置
     * @param digit 数组中10进制的最大位数，1000=3位
     */
    public static void radixSort(int[] arr, int left, int right, int digit) {
        int radix = 10;
        // d 数的第几位，出入桶次数等于数组中最大的数的位数
        for (int d = 1; d <= digit; d++) {
            // 使用计数法对每个位的入桶出桶做了优化
            int[] count = new int[radix];
            // 计算词频数组，count[2] = 2，数组中所有数的该位数上2一共出现了两次
            for (int i = left; i <= right; i++) {
                int numD = getDigit(arr[i], d);
                count[numD] += 1;
            }
            // 前缀和数组，位数上小于等于该数的数有多少个，count[2] = 2，该位数上小于等于2的数有两个
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i-1];
            }
            int[] help = new int[right - left + 1];
            // 入桶时最右边得数是最后入的，出桶也是最后出，也会放到最右边（这里桶是队列）
            // 最后出的数的位置在最右侧，使用上述计数中位数出现的次数减一就是新位置（小于等于该位数的最右侧位置）
            for (int i = right; i >= left; i--) {
                int numD = getDigit(arr[i], d);
                int numIndex = count[numD] - 1;
                count[numD]--;
                help[numIndex] = arr[i];
            }
            System.arraycopy(help, 0, arr, 0, help.length);
        }
    }

    private static int getDigit(int num, int digit) {
        return (num / (int) Math.pow(10, digit - 1)) % 10;
    }

    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int value : arr) {
            max = Math.max(value, max);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

}
