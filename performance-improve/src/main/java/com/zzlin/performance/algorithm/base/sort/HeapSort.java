package com.zzlin.performance.algorithm.base.sort;

import java.util.Random;

/**
 * 堆排序
 * time O(N*logN)，heap O(1)，不能做到稳定性
 * 只有堆排序能做到时间复杂度为O(N*logN)，空间复杂度为O(1)
 * @author zlin
 * @date 20220611
 */
public class HeapSort {

    public static void main(String[] args) {
//        int[] arr = new int[]{8,5,6,9,1,3,4,7,2};
        int[] arr = new int[10];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt();
        }
        heapSort(arr);
        for (int value : arr) {
            System.out.print(value + ", ");
        }

        System.out.println();

        heapSort2(arr);
        for (int value : arr) {
            System.out.print(value + ", ");
        }
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 先把数组高成一个大顶堆
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        int heapSize = arr.length;
        // 把最大值放到最后，即排好了一个位置
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            // 重新高成一个大顶堆
            heapIfy(arr, 0, heapSize);
            // 再把顶放到最后没排好的位置，排好倒数第二、第三...位
            swap(arr, 0, --heapSize);
        }
    }

    /**
     * 生成堆结构：将每一个新增节点向上堆化到合适的位置
     *
     * 将index位置的数向上移动到大顶堆的合适的位置
     */
    public static void heapInsert(int[] arr, int index) {
        int parent;
        // 如果当前位置比他的父位置大，则将当前位置和父位置进行交换，将当前位置改为父位置继续判断
        while (arr[index] > arr[parent = ((index - 1) / 2)]) {
            swap(arr, index, parent);
            index = parent;
        }
    }

    /**
     * 向下堆化
     * 移除定节点后，将最后一个节点复制到顶节点，然后和左右孩子比较以找到合适的位置
     *
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

    /************************网上的方式*******************************/
    public static void heapSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int heapSize = arr.length;
        buildMaxHeap2(arr, heapSize);
        for (int i = heapSize - 1; i > 0; i--) {
            swap(arr,0, i);
            heapSize--;
            heapIfy2(arr, 0, heapSize);
        }
    }

    public static void buildMaxHeap2(int[] arr, int heapSize) {
        for (int i = (heapSize - 1) / 2; i >= 0; i--) {
            heapIfy2(arr, i, heapSize);
        }
    }

    public static void heapIfy2(int[] arr, int index, int heapSize) {
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
            heapIfy2(arr, largest, heapSize);
        }

    }
    /************************网上的方式*******************************/

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
