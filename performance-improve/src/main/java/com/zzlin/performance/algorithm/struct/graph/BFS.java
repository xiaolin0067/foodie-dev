package com.zzlin.performance.algorithm.struct.graph;

import com.zzlin.performance.algorithm.struct.graph.struct.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的宽度优先遍历
 * 先遍历节点的指出去的节点，即nexts节点
 * @author zlin
 * @date 20220717
 */
public class BFS {

    /**
     * 从图的一个节点，进行宽度优先遍历
     * 图的宽度优先遍历
     *   1，利用队列实现
     *   2，从源节点开始依次按照宽度进队列，然后弹出
     *   3，每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
     *   4，直到队列变空
     */
    public static void bfs(Node node) {
        if (node == null) {
            return;
        }
        System.out.println("图的宽度优先遍历：");
        Queue<Node> queue = new LinkedList<>();
        // 记录已经走过的节点，避免重复走
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.value);
            for (Node next : cur.nexts) {
                // 若节点已经走过了，不在遍历
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

}
