package com.zzlin.performance.algorithm.base;

/**
 * 堆排序
 * time O(N * logN)
 * heap O(1)
 * 只有堆排序能坐到空间复杂度为O(1)
 * @author zlin
 * @date 20220611
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        heapSort(arr);
        for (int value : arr) {
            System.out.print(value + "");
        }

        System.out.println();

    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapIfy(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    /**
     * 将index位置的数向上移动到大顶堆的合适的位置
     */
    public static void heapInsert(int[] arr, int index) {
        // 如果当前位置比他的父位置大，则将当前位置和父位置进行交换，将当前位置改为父位置继续判断
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index -1) / 2);
            index = (index -1) / 2;
        }
    }

    /**
     * 堆化
     * 将index位置的数向下移动到大顶堆的合适的位置
     */
    public static void heapIfy(int[] arr, int index, int heapSize) {
        int leftChild = index * 2 + 1;
        while (leftChild < heapSize) {
            int rightChild = leftChild + 1;
            int largest = rightChild < heapSize && arr[rightChild] > arr[leftChild]
                    ? rightChild : leftChild;
            largest = arr[largest] > arr[index] ? largest : index;
            // 找到节点与其左右子节点中谁最大，如果自己最大，则退出
            if (index == largest) {
                break;
            }
            // 交换节点与子节点最大的节点位置
            swap(arr, index, largest);
            // 以子节点位置作为当前位置
            index = largest;
            // 计算子节点的左子节点
            leftChild = index * 2 + 1;
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
