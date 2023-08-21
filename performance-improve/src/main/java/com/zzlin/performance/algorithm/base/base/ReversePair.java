package com.zzlin.performance.algorithm.base.base;

/**
 * 逆序对：
 *   在一个数组中，左边的数如果比右边的数大，则这两个数构成一个逆序对
 *   [3,2,4,5,0]
 *   3,2 | 3,0 | 2,0 | 4,0 | 5,0
 * @author zlin
 * @date 20220607
 */
public class ReversePair {

    public static void main(String[] args) {
        int[] arr1 = new int[]{3,2,4,5,0};
        System.out.println("逆序对总数：" + reversePair(arr1, 0, arr1.length - 1));
    }

    public static int reversePair(int[] arr, int left, int right) {
        if (left > right) {
            throw new RuntimeException("参数错误");
        }
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return reversePair(arr, left, mid) +
                reversePair(arr, mid + 1, right) +
                marge(arr, left, mid, right);
    }

    public static int marge(int[] arr, int left, int mid, int right) {
        int[] margeArray = new int[right - left + 1];
        int margeArrayIndex = 0;
        int p0 = left;
        int p1 = mid + 1;
        int reversePair = 0;
        while (p0 <= mid && p1 <= right) {
            if (arr[p0] > arr[p1]) {
                reversePair += mid - p0 + 1;
                for (int i = p0; i <= mid; i++) {
                    System.out.println("逆序对：" + arr[i] + ", " + arr[p1]);
                }
                margeArray[margeArrayIndex++] = arr[p1++];
            }else {
                margeArray[margeArrayIndex++] = arr[p0++];
            }
        }
        while (p0 <= mid) {
            margeArray[margeArrayIndex++] = arr[p0++];
        }
        while (p1 <= right) {
            margeArray[margeArrayIndex++] = arr[p1++];
        }
        System.arraycopy(margeArray, 0, arr, left, margeArray.length);
        return reversePair;
    }

}
