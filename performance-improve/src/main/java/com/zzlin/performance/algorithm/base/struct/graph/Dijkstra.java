package com.zzlin.performance.algorithm.base.struct.graph;

import com.zzlin.performance.algorithm.base.struct.graph.struct.Edge;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Graph;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Node;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zlin
 * @date 20220720
 */
public class Dijkstra {

    public static void main(String[] args) {
        Integer[][] graphArray = new Integer[][]{
                {5,0,1},{20,0,2},{17,0,3},{2,1,2},{1,2,3},{10,1,4},{5,3,4}
        };
        Graph graph = GraphGenerator.createGraphByTwoDimensionArray(graphArray);
        Node node = graph.nodes.get(0);
        Map<Node, Integer> dijkstra = dijkstra(node);
        dijkstra.forEach((key, val) -> {
            System.out.println(key.getValue() + ": " + val);
        });
    }

    /**
     * Dijkstra算法
     * 找到所有节点与head节点的最短距离
     * 适用范围：没有累加和为负数的环，将导致一直转圈来缩小权值
     *
     * 狄克斯特拉算法。是从一个顶点到其余各顶点的最短路径算法，解决的是有权图中最短路径问题。
     * 迪杰斯特拉算法主要特点是从起始点开始，采用贪心算法的策略，每次遍历到始点距离最近且未访问过的顶点的邻接节点，直到扩展到终点为止。
     * 还有一种 A* 算法，无需了解
     */
    public static Map<Node, Integer> dijkstra(Node head) {
        if (head == null) {
            return null;
        }
        // 存放的是从head到所有节点的最短距离，key=节点，value=从head到达key的节点的最短距离
        // 若节点不存在于该map代表从head到节点的距离是无穷
        Map<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(head, 0);
        // 节点是否已被选择处理过，避免循环处理
        Set<Node> selectedSet = new HashSet<>();
        // 未被选择处理过的与head距离最短的节点
        Node minNode = getMinDistanceUnselectedNode(distanceMap, selectedSet);
        while (minNode != null) {
            selectedSet.add(minNode);
            // 当前节点与head的最短距离
            int minNodeDistance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                // 当前边指向节点与head的距离
                int newDistance =  minNodeDistance + edge.weight;
                Integer oldDistance = distanceMap.get(toNode);
                // 若节点与head的最短距离还没有，就把当前的距离记录为最短记录，否则与原来的距离较小者为最短距离
                int minDistance = oldDistance == null ? newDistance : Math.min(oldDistance, newDistance);
                distanceMap.put(toNode, minDistance);
            }
            // 最后全部加入selectedSet后会无节点可选，退出循环
            minNode = getMinDistanceUnselectedNode(distanceMap, selectedSet);
        }
        return distanceMap;
    }

    /**
     * 从距离集合中选择一个不在已选择过的节点列表里的最短距离的节点
     * @param distanceMap 节点距离集合
     * @param selectedSet 已选择过的节点
     * @return 未被选择过的最短距离的节点
     */
    private static Node getMinDistanceUnselectedNode(Map<Node, Integer> distanceMap, Set<Node> selectedSet) {
        if (distanceMap == null || selectedSet == null) {
            return null;
        }
        Pair<Node, Integer> pair = Pair.of(null, Integer.MAX_VALUE);
        // 有一个堆的优化：这里选最小值是遍历所有的节点和其距离，可以改为堆结构来做优化
        // 但是此处使用常规的堆（系统实现的堆）结构并不会达到优化的目的，因为一个节点的出现可能会影响已有的节点的原来最短距离
        // 系统实现的堆（所有语言的都一样），只能是给一堆数据，给你最小/大的数据，堆中所有数据值不能变，否则重新全局扫描将导致时间复杂度与遍历差不多
        // 堆的改写（当堆中的数据会发生变化时）：需要自己建一个堆，当堆中节点的值发生变化时，从这个堆的局部，这个值开始往上或往下调整。
        for (Map.Entry<Node, Integer> distance : distanceMap.entrySet()) {
            // 如果node已被选择过或者是距离大于当前最小的距离的话跳过
            if (selectedSet.contains(distance.getKey()) || distance.getValue() >= pair.getRight()) {
                continue;
            }
            // 节点不在已选择过的节点列表里且距离更短
            pair = Pair.of(distance.getKey(), distance.getValue());
        }
        return pair.getLeft();
    }

}
