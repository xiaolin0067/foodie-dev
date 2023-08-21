package com.zzlin.performance.algorithm.base.struct.graph;

import com.zzlin.performance.algorithm.base.struct.graph.struct.Edge;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Graph;
import com.zzlin.performance.algorithm.base.struct.graph.struct.Node;

/**
 * 将其他的表示图的方式转换为自己的方式
 * @author zlin
 * @date 20220716
 */
public class GraphGenerator {

    /**
     * 通过二维数组创建图
     * 二维数组需要满足如下约定
     * [
     *   [weight,from,to],
     *   [weight,from,to]
     * ]
     * 在只有城市或数据范围不大的情况下，可以将nodes的HashMap换成数组会更快
     * @return Graph
     */
    public static Graph createGraphByTwoDimensionArray(Integer[][] matrix) {
        if (matrix.length < 1) {
            return null;
        }
        Graph graph = new Graph();
        for (Integer[] row : matrix) {
            Integer weight = row[0];
            Integer fromVal = row[1];
            Integer toVal = row[2];
            if (!graph.nodes.containsKey(fromVal)) {
                graph.nodes.put(fromVal, new Node(fromVal));
            }
            if (!graph.nodes.containsKey(toVal)) {
                graph.nodes.put(toVal, new Node(toVal));
            }
            Node fromNode = graph.nodes.get(fromVal);
            Node toNode = graph.nodes.get(toVal);
            // 填充属性
            Edge edge = new Edge(weight,fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.edges.add(edge);
            fromNode.out++;
            toNode.in++;
            graph.edges.add(edge);
        }
        return graph;
    }

}
