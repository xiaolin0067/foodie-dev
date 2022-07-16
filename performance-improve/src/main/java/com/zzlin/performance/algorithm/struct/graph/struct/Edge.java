package com.zzlin.performance.algorithm.struct.graph.struct;

/**
 * 有向边
 * 无向边是特殊的有向图，其边用两个有向边的拼起来
 * @author zlin
 * @date 20220716
 */
public class Edge {
    /**
     * 权重
     */
    public int weight;
    /**
     * 边的出发节点
     */
    public Node from;
    /**
     * 边的到达节点
     */
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
