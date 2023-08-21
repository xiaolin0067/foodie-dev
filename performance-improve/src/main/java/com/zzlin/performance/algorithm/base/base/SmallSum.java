package com.zzlin.performance.algorithm.base.base;

/**
 * 求小和：
 *   在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和
 *   [1,3,4,2,5]
 *   1左边比1小的数，没有；
 *   3左边比3小的数，1；
 *   4左边比4小的数，1、3；
 *   2左边比4小的数，1
 *   5左边比5小的数，1、3、4、2
 *   即：1+1+3+1+1+3+4+2=16
 *   可以转换为：
 *   右边比1大的数有：3、4、2、5，共4个数，1会被计算四次小和，1*4=4
 *   右边比3大的数有：4、5，共2个数，3会被计算俩次小和，3*2=6
 *   右边比4大的数有：5，共1个数，4会被计算一次小和，4*1=4
 *   右边比2大的数有：5，共1个数，4会被计算一次小和，2*1=2
 *   右边比5大的数没有
 *   即：4+6+4+2=16
 * @author zlin
 * @date 20220607
 */
public class SmallSum {

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,4,2,5};
        System.out.println(smallSum(arr, 0, arr.length - 1));
    }

    public static int smallSum(int[] arr, int left, int right) {
        if (left > right) {
            throw new RuntimeException("参数错误");
        }
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return smallSum(arr, left, mid) +
                smallSum(arr, mid + 1, right) +
                marge(arr, left, mid, right);
    }

    public static int marge(int[] arr, int left, int mid, int right) {
        int[] margeArray = new int[right - left + 1];
        int margeArrayIndex = 0;
        int p0 = left;
        int p1 = mid + 1;
        int smallSum = 0;
        while (p0 <= mid && p1 <= right) {
            // 关键步骤，因合并的数组都是有序的，若左侧p0节点小于右侧p1节点，则右侧p1节点后的节点都比p0大，需要累加小和
            smallSum += arr[p0] < arr[p1] ? (right - p1 + 1) * arr[p0] : 0;
            margeArray[margeArrayIndex++] = arr[p0] <= arr[p1] ? arr[p0++] : arr[p1++];
        }
        while (p0 <= mid) {
            margeArray[margeArrayIndex++] = arr[p0++];
        }
        while (p1 <= right) {
            margeArray[margeArrayIndex++] = arr[p1++];
        }
        System.arraycopy(margeArray, 0, arr, left, margeArray.length);
        return smallSum;
    }

}
