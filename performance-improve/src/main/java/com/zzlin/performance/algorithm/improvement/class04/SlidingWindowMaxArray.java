package com.zzlin.performance.algorithm.improvement.class04;

import java.util.LinkedList;

/**
 * 由一个代表题目，引出一种结构-双端队列
 * [题目]
 * 有一个整型数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次向右边滑一个位置。
 * 例如，数组为[4,3,5,4,3,3,6,7]，窗口大小为3时:
 * [4 3 5]4 3 3 6 7      窗口中最大值为5
 * 4[3 5 4]3 3 6 7       窗口中最大值为5
 * 4 3[5 4 3]3 6 7       窗口中最大值为5
 * 4 3 5[4 3 3]6 7       窗口中最大值为4
 * 4 3 5 4[3 3 6]7       窗口中最大值为6
 * 4 3 5 4 3[3 6 7]      窗口中最大值为7
 *
 * 如果数组长度为n, 窗口大小为w, 则一共产生n-w+1个窗口的最大值
 * 请实现一个函数
 * 输入: 整型数组arr, 窗口小为w
 * 输出: 一个长度为n-w+1的数组res, res[i]表示每一种窗口状态下的最大值
 * 以本题为例，结果应该返回[5,5,5,4,6,7]
 *
 * @author zlin
 * @date 20230312
 */
public class SlidingWindowMaxArray {

    public static void main(String[] args) {
        int[] maxWindow = getMaxWindow(new int[]{4,3,5,4,3,3,6,7}, 3);
        for (int i : maxWindow) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * 滑动窗口取最大值结构
     */
    public static class WindowMax {

        private int l;
        private int r;
        /**
         * arr[ [L..R) ]
         */
        private final int[] arr;
        private final LinkedList<Integer> qmax;

        public WindowMax(int[] a) {
            arr = a;
            l = -1;
            r = 0;
            qmax = new LinkedList<>();
        }

        public void addNumFromRight() {
            if (r == arr.length) {
                return;
            }
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[r]) {
                qmax.pollLast();
            }
            qmax.addLast(r);
            r++;
        }

        public void removeNumFromLeft() {
            if (l >= r - 1) {
                return;
            }
            l++;
            if (qmax.peekFirst() == l) {
                qmax.pollFirst();
            }
        }

        public Integer getMax() {
            if (!qmax.isEmpty()) {
                return arr[qmax.peekFirst()];
            }
            return null;
        }
    }

    /**
     * 如果数组长度为n, 窗口大小为w, 则一共产生n-w+1个窗口的最大值
     * 请实现一个函数
     * 输入: 整型数组arr, 窗口小为w
     * 输出: 一个长度为n-w+1的数组res, res[i]表示每一种窗口状态下的最大值
     * 以本题为例，结果应该返回[5,5,5,4,6,7]
     *
     * @param arr 原始数组 n
     * @param w 窗口大小
     * @return res[i]表示每一种窗口状态下的最大值
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        // 装下标, 值：大 -> 小
        LinkedList<Integer> qmax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        // 窗口
        for (int i = 0; i < arr.length; i++) {
            // R往右移动，让最右的小于当前值的都弹出
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i);
            // L往右移动，弹出不在窗口的下标
            if (qmax.peekFirst() == (i - w)) {
                qmax.pollFirst();
            }
            // 窗口形成了，将窗口的最大值放入结果集合
            if (i >= w - 1) {
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

}
