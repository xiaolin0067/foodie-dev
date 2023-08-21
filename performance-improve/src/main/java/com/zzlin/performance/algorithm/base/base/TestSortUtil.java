package com.zzlin.performance.algorithm.base.base;

import com.zzlin.performance.algorithm.base.base.sort.*;

import java.util.Random;

/**
 * @author zlin
 * @date 20220704
 */
public class TestSortUtil {

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            int[] arr = generateRandomArray();
            QuickSort.quickSort(arr, 0, arr.length - 1);
//            SelectionSort.selectionSort(arr);
//            MargeSort.margeSort(arr, 0, arr.length - 1);
//            InsertionSort.insertionSort(arr);
//            HeapSort.heapSort(arr);
            quickSort(arr);
            BubbleSort.bubbleSort(arr);
            boolean sorted = isSorted(arr);
            StringBuilder sb = new StringBuilder();
            if (!sorted) {
                sb.append("排序失败：");
            }
            for (int num : arr) {
                sb.append(num).append(", ");
            }
            System.out.println(sb.toString());
        }
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        // 随机一个数放到数组最后
        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        // 把最后一个数做荷兰国旗划分
        int[] p = partition(arr, left, right);
        // 左边递归
        quickSort(arr, right, p[0] - 1);
        quickSort(arr, p[1] + 1, right);

    }

    private static int[] partition(int[] arr, int left, int right) {

        int num = arr[right];
        for (int i = left; i <= right;) {
            if (arr[i] < num) {
                swap(arr, left++, i++);
            } else if (arr[i] > num) {
                swap(arr, right--, i);
            }else {
                i++;
            }
        }

        return new int[]{left, right};
    }

    private static void swap(int[] arr, int a, int b) {
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    private static int[] generateRandomArray() {
        int[] res = new int[200];
        Random random = new Random();
        for (int i = 0; i < res.length; i++) {
            res[i] = random.nextInt();
        }
        return res;
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if(arr[i-1] > arr[i]){
                return false;
            }
        }
        return true;
    }

}
