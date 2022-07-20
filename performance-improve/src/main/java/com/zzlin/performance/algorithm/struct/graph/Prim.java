package com.zzlin.performance.algorithm.struct.graph;

import com.zzlin.performance.algorithm.struct.graph.struct.Edge;
import com.zzlin.performance.algorithm.struct.graph.struct.Graph;
import com.zzlin.performance.algorithm.struct.graph.struct.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 普里姆算法
 * 最小生成树
 * 普里姆算法（Prim算法），图论中的一种算法，可在加权连通图里搜索最小生成树。
 * 意即由此算法搜索到的边子集所构成的树中，不但包括了连通图里的所有顶点，且其所有边的权值之和亦为最小。
 * @author zlin
 * @date 20220720
 */
public class Prim {

    public static Set<Edge> primMST(Graph graph) {
        if (graph == null) {
            return null;
        }
        // 挑选的边放到结果集
        Set<Edge> result = new HashSet<>();
        // 经过的点的集合里
        Set<Node> nodeSet = new HashSet<>();
        // 解锁的边方法小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        // 从图中随便选一个点作为开始点，若整个图是连通的没有必要用这个for，直接随机取一个
        for (Node node : graph.nodes.values()) {
            if (nodeSet.contains(node)) {
                continue;
            }
            // 加入到已经过的节点集合里
            nodeSet.add(node);
            // 解锁这个点的所有边
            priorityQueue.addAll(node.edges);
            while (!priorityQueue.isEmpty()) {
                // 弹出所有解锁的边权重最小的
                Edge edge = priorityQueue.poll();
                Node toNode = edge.to;
                if (nodeSet.contains(toNode)) {
                    continue;
                }
                // to节点为经过过，将该边加入最小结果集，节点加入已经过的节点集合，并将其边都解锁
                result.add(edge);
                nodeSet.add(toNode);
                priorityQueue.addAll(toNode.edges);
            }
        }
        return result;
    }

}
