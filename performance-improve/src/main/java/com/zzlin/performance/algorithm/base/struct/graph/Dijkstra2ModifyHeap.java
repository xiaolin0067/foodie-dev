package com.zzlin.performance.algorithm.base.struct.graph;

import com.zzlin.performance.algorithm.base.struct.graph.struct.Edge;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Graph;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Dijkstra问题，改写堆的优化
 *
 * @author zlin
 * @date 20220720
 */
public class Dijkstra2ModifyHeap {

    public static void main(String[] args) {
        Integer[][] graphArray = new Integer[][]{
                {5,0,1},{20,0,2},{17,0,3},{2,1,2},{1,2,3},{10,1,4},{5,3,4}
        };
        Graph graph = GraphGenerator.createGraphByTwoDimensionArray(graphArray);
        Node node = graph.nodes.get(0);
        Map<Node, Integer> dijkstra = dijkstra(node, 5);
        dijkstra.forEach((key, val) -> {
            System.out.println(key.getValue() + ": " + val);
        });
    }

    /**
     * Dijkstra算法，堆的优化
     *
     * 从head出发，所有head能到达的节点，生成到达每个节点的最小路径并返回
     *
     * @param head 开始的头节点
     * @param size 共有多少个节点
     * @return head到每个节点的距离
     */
    public static Map<Node, Integer> dijkstra(Node head, int size) {
        if (head == null) {
            return null;
        }
        // 存放的是从head到所有可达节点的最短距离，key=节点，value=从head到达key的节点的最短距离
        // 若节点不存在于该map代表从head到节点的距离是无穷
        Map<Node, Integer> result = new HashMap<>();

        // 自定义的堆
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        while (nodeHeap.isNotEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.getNode();
            int distance = record.getDistance();
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class NodeRecord {
        private Node node;
        private int distance;
    }

    /**
     * 小根堆
     */
    @Data
    private static class NodeHeap {
        /**
         * 堆结构
         */
        private Node[] nodes;
        /**
         * 记录node节点在nodes[]上的index
         * 重点：因为有这个记录，可以直接对值改变的节点进行重新堆化
         * key = node
         * value = node节点在nodes[]上的index
         */
        private Map<Node, Integer> heapIndexMap;
        /**
         * 记录node到head的当前最短距离
         * key = node
         * value = node到head的当前最短距离
         */
        private Map<Node, Integer> distanceMap;
        /**
         * 目前堆上一共有多少个节点
         */
        private int size;
        /**
         * 初始化自定义堆
         * @param size 堆的最大节点数
         */
        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }

        private boolean isNotEmpty() {
            return size != 0;
        }

        /**
         * 节点是否入堆过
         *
         * @param node 节点
         * @return 是否曾经入堆过
         */
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         * 节点是否在堆上
         */
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distance, distanceMap.get(node)));
                insertHeapIfy(heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapIfy(size++);
            }
        }

        private NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapIfy(0, --size);
            return nodeRecord;
        }

        private void heapIfy(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int right = left + 1;
                int smallest = right < size && distanceMap.get(nodes[right]) < distanceMap.get(nodes[left])
                        ? right : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (index == smallest) {
                    break;
                }
                swap(index, smallest);
                // 以子节点位置作为当前位置
                index = smallest;
                // 计算子节点的左子节点
                left = index * 2 + 1;
            }
        }

        private void insertHeapIfy(int index) {
            int parent;
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[parent = ((index - 1) / 2)])) {
                swap(index, parent);
                index = parent;
            }
        }

        /**
         * 交换位置
         */
        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }
}
