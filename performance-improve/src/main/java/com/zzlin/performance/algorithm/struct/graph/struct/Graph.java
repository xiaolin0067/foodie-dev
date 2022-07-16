package com.zzlin.performance.algorithm.struct.graph.struct;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图
 * 能满足绝大多数场景下的使用的结构
 * @author zlin
 * @date 20220716
 */
public class Graph {

    public HashMap<Integer, Node> nodes;
    public HashSet<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }
}
