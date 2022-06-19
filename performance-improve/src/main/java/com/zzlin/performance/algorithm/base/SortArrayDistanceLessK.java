package com.zzlin.performance.algorithm.base;

import java.util.PriorityQueue;

/**
 * 一个数组几乎有序（变为有序最多移动k次），将其排序
 * @author zlin
 * @date 20220611
 */
public class SortArrayDistanceLessK {

    public static void main(String[] args) {
        // Java自带的小根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(8);
        queue.add(5);
        queue.add(6);
        queue.add(9);
        queue.add(1);
        queue.add(3);
        queue.add(4);
        queue.add(7);
        queue.add(2);
        while (!queue.isEmpty()) {
            System.out.print(queue.poll());
        }
        System.out.println();
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        sortArrayDistanceLessK(arr, 10);
        for (int value : arr) {
            System.out.print(value);
        }
    }

    /**
     * 若数组中的每个数最多移动k次就能到达正确的位置
     * 那么arr[0]位置的数在arr[0~k]内
     * 先把arr[0~k]位置的数放到小根堆，将根节点移除放到arr[0]
     * 将arr[k+1]放入小根堆，将根节点移除放到arr[2]
     * 若k+2不存在了，则直接将根节点不停移除当道数组中即可
     * @param arr 待排数组
     * @param k 数字最多移动k次
     */
    public static void sortArrayDistanceLessK(int[] arr, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        int min = Math.min(arr.length - 1, k);
        for (;index <= min; index++) {
            heap.add(arr[index]);
        }
        int i = 0;
        for (;index < arr.length; i++, index++) {
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

}
