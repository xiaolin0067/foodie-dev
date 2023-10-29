package com.zzlin.performance.algorithm.improvement.middle.class01;

import java.util.Arrays;

/**
 * 给定一个有序数组arr，代表数轴上从左到右有n个点arr[0]、arr[1]...arr[n-1]
 * 给定一个正数L，代表一根长度为L的绳子，求绳子最多能覆盖其中的几个点。
 *
 * 方法1：
 * 时间复杂度：O(n * log(n))
 * 将绳子的最右侧放到数组的最左侧开始，看能覆盖几个点
 * 依次往右移动，直到绳子完全覆盖到树组上
 *
 * 方法2:
 * 时间复杂度：O(n)
 * 将绳子的最右侧放到数组的最右侧，绳子的最左侧最长能够到的数组的最左侧
 * 计算改节点开始时能覆盖几个节点
 * 绳子左侧向右移动，看右侧能否继续向右移动，然后计算能覆盖多少的节点
 * 绳子左右侧单调递增
 *
 * @author lin
 */
public class CordCoverMaxPoint {

    /**
     * 计算覆盖多少个点
     *
     * @param arr 节点数组
     * @param l 绳子的长度
     * @return 最大覆盖多少个节点
     */
    private static int maxPoint2(int[] arr, int l) {
        int left = 0;
        int right = 0;
        int max = 0;
        while (left < arr.length) {
            while (right < arr.length && arr[right] - arr[left] <= l) {
                right++;
            }
            max = Math.max(max, right - left++);
        }
        return max;
    }

    /**
     * 生成随机数组，for test
     *
     * @param len 长度
     * @param max 最大值
     * @return 随机数组
     */
    private static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static int maxPoint1(int[] arr, int L) {
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            int nearest = nearestIndex(arr, i, arr[i] - L);
            res = Math.max(res, i - nearest + 1);
        }
        return res;
    }

    public static int nearestIndex(int[] arr, int R, int value) {
        int L = 0;
        int index = R;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            if (ans1 != ans2) {
                System.out.println("oops!");
                break;
            }
        }
    }

}
