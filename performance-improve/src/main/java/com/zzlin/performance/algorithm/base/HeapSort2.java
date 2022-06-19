package com.zzlin.performance.algorithm.base;

/**
 * 网上的另一种堆排序
 * @author zlin
 * @date 20220611
 */
public class HeapSort2 {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        heapSort(arr);
        for (int value : arr) {
            System.out.print(value + "");
        }
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int heapSize = arr.length;
        buildMaxHeap(arr, heapSize);
        for (int i = heapSize - 1; i > 0; i--) {
            swap(arr,0, i);
            heapSize--;
            heapIfy(arr, 0, heapSize);
        }
    }

    public static void buildMaxHeap(int[] arr, int heapSize) {
        for (int i = (heapSize - 1) / 2; i >= 0; i--) {
            heapIfy(arr, i, heapSize);
        }
    }

    public static void heapIfy(int[] arr, int index, int heapSize) {
        int leftChild = index * 2 + 1;
        int rightChild = leftChild + 1;
        int largest = index;
        if (leftChild < heapSize && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }
        if (rightChild < heapSize && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }
        if (largest != index) {
            swap(arr, index, largest);
            heapIfy(arr, largest, heapSize);
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
