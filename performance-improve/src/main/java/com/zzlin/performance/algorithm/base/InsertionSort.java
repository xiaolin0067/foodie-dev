package com.zzlin.performance.algorithm.base;

/**
 * @author zlin
 * @date 20220605
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        insertionSort(arr);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    /**
     * 插入排序
     * 就像手里的牌是顺序排列的，拿到第一张，是有序的，拿到第二张，和已有的最后的位置开始比较并交换
     * 最好的情况下是时间复杂度是：Ω(N)
     * 算法流程按照最差的情况来估计时间复杂度：O(N^2)
     * 0~0保证有序
     * 0~1保证有序，1与0比较，若小于则交换
     * 0~2保证有序，2与1比较，若小于则交换，与0比较，小于则交换
     */
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j] < arr[j-1]; j--) {
                swap(arr, j, j-1);
            }
        }
    }

    public static void swap(int[] arr, int index1, int index2) {
        if (index1 == index2) {
            return;
        }
        arr[index1] = arr[index1] ^ arr[index2];
        arr[index2] = arr[index1] ^ arr[index2];
        arr[index1] = arr[index1] ^ arr[index2];
    }

}
