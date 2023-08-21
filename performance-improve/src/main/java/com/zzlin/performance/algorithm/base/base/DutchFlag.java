package com.zzlin.performance.algorithm.base.base;

/**
 * 问题二(荷兰国旗问题)
 * 给定一个数组arr，和一个数num，请把小于num的数放在数组的左边，等于num的数放在数组的中间，大于num的数放在数组的右边。
 * 要求额外空间复杂度0(1)，时间复杂度0(N)
 * @author zlin
 * @date 20220608
 */
public class DutchFlag {

    public static void main(String[] args) {
        int[] arr = new int[]{3,5,6,7,4,3,5,8};
        divideArray(arr, 5);
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
        arr = new int[]{3,5,6,3,4,5,2,6,9,0};
        dutchFlag(arr, 5);
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

    /**
     * 给定一个数组arr，和一个数num，请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
     * 要求额外空间复杂度0(1)，时间复杂度0(N)
     * 54:50
     */
    public static void divideArray(int[] arr, int num) {
        // 比num小的范围，初始为0，比该值小的都是满足条件的
        int left = 0;
        for (int i = 0; i < arr.length; i++) {
            // 若移动位置的值小于等于给定的值，与当前的left位置交换，并扩大该left
            if (arr[i] <= num) {
                swap(arr, i, left++);
            }
        }
    }

    /**
     * 问题二(荷兰国旗问题)
     * 给定一个数组arr，和一个数num，请把小于num的数放在数组的左边，等于num的数放在数组的中间，大于num的数放在数组的右边。
     * 要求额外空间复杂度0(1)，时间复杂度0(N)
     */
    public static void dutchFlag(int[] arr, int num) {
        // 初始化小于num的右边界为第一个位置，大于num的左边界为最后一个位置
        int left = 0;
        int right = arr.length - 1;
        for (int i = 0; i <= right;) {
            if (arr[i] < num) {
                // 若当前数小于num，则将当前数交换到小于num范围的右边界，右边界++，指针++
                swap(arr, i++, left++);
            }else if (arr[i] > num) {
                // 若当前数大于num，则将当前数交换到大于num范围的左边界，左边界--，指针不变
                swap(arr, i, right--);
            }else {
                // 若当前数等于num，则指针++即可
                i++;
            }
        }
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
