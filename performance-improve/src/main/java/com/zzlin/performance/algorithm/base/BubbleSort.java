package com.zzlin.performance.algorithm.base;

/**
 * 冒泡排序
 * @author zlin
 * @date 20220602
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        bubbleSort(arr);
        for (int value : arr) {
            System.out.print(value + "");
        }
    }

    /**
     * 冒泡排序
     * 在0~N-1上比较交换相邻的两个值，N-1位置上冒出最大值
     * 在0~N-2上比较交换相邻的两个值，N-2位置上冒出最大值
     * O(N^2)
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
    }

    /**
     * 异或：相同为0不同为1
     * 其效果可以理解为：不进位的相加
     * 有以下性质：
     *   1、0^N = N, N^N = 0
     *   2、满足交换律结合律，a^b=b^a, (a^b)^c=a^(b^c)
     *   3、一堆数随便怎么两两异或最后的结果是一样的
     */
    public static void swap(int[] arr, int a, int b) {
        // 交换的两个数的内存地址不能一样，否则将导致将数改为0
        if (a == b) {
            return;
        }
        // 对于异或有：0^N = N, N^N = 0
        arr[a] = arr[a] ^ arr[b];
        // arr[b] = arr[a] ^ arr[b] ^ arr[b] = arr[a]
        arr[b] = arr[a] ^ arr[b];
        // arr[a] = arr[a] ^ arr[b] ^ arr[a] = arr[b]
        arr[a] = arr[a] ^ arr[b];
    }

}
