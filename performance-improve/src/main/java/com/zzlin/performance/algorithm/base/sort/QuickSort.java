package com.zzlin.performance.algorithm.base.sort;

/**
 * 快速排序
 * O(N^2)
 * 加上随机分组，则为O(N*logN)，不能做到稳定性
 * 空间复杂度 O(logN)
 * @author zlin
 * @date 20220608
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[]{4,3,5,6,5,0,1,7,8,5};
        quickSort(arr, 0, arr.length - 1);
        for (int value : arr) {
            System.out.print(value + ", ");
        }
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
//        if (left > right - 60) {
//            在arr[left~right]上使用插入排序，O(N^2)算法在小样本量时跑得快
//            return;
//        }
        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        int[] p = partition(arr, left, right);
        quickSort(arr, left, p[0]-1);
        quickSort(arr, p[1]+1, right);
    }

    private static int[] partition(int[] arr, int left, int right) {
        int num = arr[right];
        for (int i = left; i <= right;) {
            if (arr[i] < num) {
                swap(arr, i++, left++);
            }else if (arr[i] > num) {
                swap(arr, i, right--);
            }else {
                i++;
            }
        }
        return new int[]{left, right};
    }

    public static void swap(int[] arr, int a, int b) {
        // 这个判断非常重要，必须带上
        if (a == b) {
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

}
