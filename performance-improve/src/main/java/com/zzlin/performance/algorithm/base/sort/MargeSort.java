package com.zzlin.performance.algorithm.base.sort;

/**
 * 归并排序
 * 时间复杂度O(N*logN)，空间复杂度O(N)，可以做到稳定性
 * @author zlin
 * @date 20220605
 */
public class MargeSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        margeSort(arr, 0, arr.length-1);
        for (int value : arr) {
            System.out.print(value + ", ");
        }
    }

    /**
     * 归并排序
     * 1)整体就是一个简单递归，左边排好序、右边排好序、让其整体有序
     * 2)让其整体有序的过程里用了外排序方法（将排序结果放到一个外部数组中）
     * 3)利用master公式来求解时间复杂度
     * 4)归并排序的实质
     * 时间复杂度O(N*logN)，额外空间复杂度O(N)
     *
     * T(N) = 2 * T(N/2) + O(N)
     * log(b,a)=1 = d=1 -> 复杂度为O(N ^ d * logN) -> O(N * logN)
     * 可以看到该算法时间复杂度有所降低，是因为这种方式没有浪费比较的结果
     * 每次比较都将比较结果变成了一个整体有序的部分，然后这个部分又和一个部分进行marge
     * 比较结果变成了一个有序的数组往下传递在和其他有序的数组marge
     */
    public static void margeSort(int[] arr, int left, int right) {
        if (left > right) {
            throw new RuntimeException("参数错误");
        }
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        margeSort(arr, left, mid);
        margeSort(arr, mid + 1, right);
        // 这一步是O(2N) = O(N)
        marge(arr, left, mid, right);
    }

    public static void marge(int[] arr, int left, int mid, int right) {
        int[] margeArray = new int[right - left + 1];
        int margeArrayIndex = 0;
        int p0 = left;
        int p1 = mid + 1;
        // 把拆开的两个有序数组合并到一个数组中，在两个数组中指针指向的数谁小取谁，指针加一
        // 任何一个指针结束后，将另外一个数组剩余的数全部加入到合并的数组中
        while (p0 <= mid && p1 <= right) {
            margeArray[margeArrayIndex++] = arr[p0] <= arr[p1] ? arr[p0++] : arr[p1++];
        }
        // 将p0指针数据剩余的数全部添加至合并的数组中
        while (p0 <= mid) {
            margeArray[margeArrayIndex++] = arr[p0++];
        }
        // 将p1指针数据剩余的数全部添加至合并的数组中
        while (p1 <= right) {
            margeArray[margeArrayIndex++] = arr[p1++];
        }
        // 以上两个只有一个会被执行到，只有一个会先结束

        System.arraycopy(margeArray, 0, arr, left, margeArray.length);
    }

}
