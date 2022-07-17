package com.zzlin.performance.algorithm.struct.graph;

import com.zzlin.performance.algorithm.struct.graph.struct.Graph;
import com.zzlin.performance.algorithm.struct.graph.struct.Node;

import java.util.*;

/**
 * 拓扑排序算法
 * @author zlin
 * @date 20220717
 */
public class TopologySort {

    /**
     * 拓扑排序
     *   项目A依赖项目B，项目B依赖项目C，各个项目之前不能循环依赖，将需要先编译的项目排序到前面
     *   适用范围: 要求有向图，且有入度为0的节点，且没有环
     *   解法：找到入度为0的节点
     *        处理该节点
     *        将节点该节点与其影响的边去除
     *        重新找到入度为0的节点（循环）
     *
     * 整个过程不修改原有的图的结构，使用新的数据结构在存储数据
     */
    public static List<Node> sortedTopology(Graph graph) {
        if (graph == null || graph.nodes == null) {
            return null;
        }
        // 所有节点的入度
        HashMap<Node, Integer> inMap = new HashMap<>(graph.nodes.size());
        // 入度为0的加入到队列
        Queue<Node> zeroInNodeQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInNodeQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while (!zeroInNodeQueue.isEmpty()) {
            Node node = zeroInNodeQueue.poll();
            result.add(node);
            for (Node next : node.nexts) {
                // 将移除的节点的影响的节点的入度减一
                inMap.put(next, inMap.get(next) - 1);
                // 若入度为0加入队列
                if (inMap.get(next) == 0) {
                    zeroInNodeQueue.add(next);
                }
            }
        }
        return result;
    }

}
