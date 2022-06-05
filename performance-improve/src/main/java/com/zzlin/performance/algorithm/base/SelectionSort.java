package com.zzlin.performance.algorithm.base;

/**
 * @author zlin
 * @date 20220602
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        selectionSort(arr);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    /**
     * 选择排序
     * 找到每一个位置的最小的数并交换到该位置
     * 遍历0~n-1 找到最小值的index 将0位置与最小index的元素交换
     * 遍历1~n-1 找到最小值的index 将1位置与最小index的元素交换
     * .
     * .
     * .
     * 时间复杂度：O(n^2)
     *   0~n-1, 1~n-1, 2~n-1 => an^2 + bn + c => n^2
     *   取最高阶项，去掉常数
     * 空间复杂度：O(1)
     *   只分配了有限个空间：int i,j,minIndex
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
    }

}
