package com.zzlin.performance.algorithm.base.struct.graph;

import com.zzlin.performance.algorithm.base.struct.graph.struct.Edge;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Graph;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Node;

import java.util.*;

/**
 * 克鲁斯卡尔算法
 * kruskal算法实现最小生成树
 * 最小生成树：保证图所有节点的连通性的前提下，使得所有边的权重之和最小（去除一些边，保证连通性，权重最低）
 * kruskal算法
 *   去除图中所有节点的边，将图中所有边排序，从最小的边开始在节点上加上，若加上形成了环则不加
 *   一开始将图中所有节点拆分为单个节点在一个集合，添加一条边时，判断这条边的from和to节点是否在同一个集合中
 *   若不在则添加这表边并将这两个节点加在同一个集合中，若在同一个集合则取消添加这条边
 *   适用范围: 要求无向图
 * @author zlin
 * @date 20220718
 */
public class Kruskal {

    public static Set<Edge> kruskalMST(Graph graph) {
        if (graph == null || graph.nodes == null || graph.edges == null) {
            return null;
        }
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        // M 条边, O(logM)
        priorityQueue.addAll(graph.edges);
        Set<Edge> result = new HashSet<>();
        // M 条边
        while (!priorityQueue.isEmpty()) {
            // O(logM)
            Edge edge = priorityQueue.poll();
            // O(1)
            if (!unionFind.isSameSet(edge.from, edge.to)) {
                unionFind.union(edge.from, edge.to);
                result.add(edge);
            }
        }
        return result;
    }

    /**
     * 并查集结构：做合并做查询都是O(1)常数级别
     * 一般数据结构达不到这种级别
     */
    public static class UnionFind {
        /**
         * key=某一节点
         * value=key节点的往上的节点
         */
        private HashMap<Node, Node> fatherMap;
        /**
         * key=某一集合的代表节点
         * value=key节点所在集合的节点个数
         */
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
        }

        /**
         * 初始化并查集
         */
        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            if (nodes == null) {
                return;
            }
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node n) {
            Deque<Node> stack = new LinkedList<>();
            while (n != fatherMap.get(n)) {
                stack.push(n);
                n = fatherMap.get(n);
            }
            while (!stack.isEmpty()) {
                fatherMap.put(stack.pop(), n);
            }
            return n;
        }

        /**
         * 判断n1, n2节点是否在同一个集合中
         */
        public boolean isSameSet(Node n1, Node n2) {
            return findFather(n1) == findFather(n2);
        }

        /**
         * 将两个节点合并到一个集合中
         */
        public void union(Node n1, Node n2) {
            if (n1 == null || n2 == null) {
                return;
            }
            Node n1Dai = fatherMap.get(n1);
            Node n2Dai = fatherMap.get(n2);
            if (n1Dai != n2Dai) {
                int n1SetSize = sizeMap.get(n1Dai);
                int n2SetSize = sizeMap.get(n2Dai);
                if (n1SetSize != n2SetSize) {
                    fatherMap.put(n1Dai, n2Dai);
                    sizeMap.put(n2Dai, n1SetSize + n2SetSize);
                    sizeMap.remove(n1Dai);
                }else {
                    fatherMap.put(n2Dai, n1Dai);
                    sizeMap.put(n1Dai, n1SetSize + n2SetSize);
                    sizeMap.remove(n2Dai);
                }
            }
        }
    }

}
