package com.zzlin.performance.algorithm.base.sort;

import java.util.Random;

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
        quickSort1(arr, 0, arr.length - 1);
        for (int value : arr) {
            System.out.print(value + ", ");
        }
    }

    private static void quickSort1(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        swap(arr, left + new Random().nextInt(right - left), right);
        int[] p = partition1(arr, left, right);
        quickSort1(arr, left, p[0] - 1);
        quickSort1(arr, p[1] + 1, right);
    }

    private static int[] partition1(int[] arr, int left, int right) {
        int num = arr[right];
        for (int i = left; i <= right;) {
            if (arr[i] < num) {
                // i++
                swap(arr, i++, left++);
            } else if (arr[i] > num) {
                swap(arr, i, right--);
            } else {
                i++;
            }
        }
        return new int[]{left, right};
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
//        if (left > right - 60) {
//            在arr[left~right]上使用插入排序，O(N^2)算法在小样本量时跑得快
//            return;
//        }
        // 随机找到一个位置，将他放到数组的最后，避免最坏O(N^2)情况，123456789
        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        // 用数组的最右侧right位置上的数做划分，划分为小于该数 等于该数 大于该数的三块区域，将这个数与大等于这个数的区域的最左侧交换
        // 然后将等于区域的左边界与右边界返回
        int[] p = partition(arr, left, right);
        // 然后再在小于该数区域与大于该数区域做递归
        quickSort(arr, left, p[0]-1);
        quickSort(arr, p[1]+1, right);
    }

    /**
     * 将数组以结束位置的数划分成三个区域，并返回等于区域的开始结束位置
     * 小于 | 等于 | 大于
     * @param arr 待排数组
     * @param left 开始位置
     * @param right 结束位置
     * @return 等于结束位置数区域的左边界与右边界
     */
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
