package com.zzlin.performance.algorithm.struct.graph.struct;

import java.util.ArrayList;

/**
 * 图中的节点
 * @author zlin
 * @date 20220716
 */
public class Node {
    /**
     * 节点存储的值
     */
    public int value;
    /**
     * 入度：被指向的边的条数
     * ->node
     */
    public int in;
    /**
     * 出度：指出去的边的条数
     * node->
     */
    public int out;
    /**
     * 指出去的节点列表：node1,node2
     * node->node1
     *  |
     *  v
     * node2
     */
    public ArrayList<Node> nexts;
    /**
     * 指出去的边的列表：l1, l2
     *     l1
     * node->node1
     *  |l2
     *  v
     * node2
     */
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}