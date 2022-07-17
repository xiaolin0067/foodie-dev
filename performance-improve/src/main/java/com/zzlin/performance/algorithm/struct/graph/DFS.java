package com.zzlin.performance.algorithm.struct.graph;

import com.zzlin.performance.algorithm.struct.graph.struct.Node;

import java.util.HashSet;
import java.util.Stack;

/**
 * 图的深度优先遍历
 * @author zlin
 * @date 20220717
 */
public class DFS {

    public static void dfs(Node node) {
        if (node == null) {
            return;
        }
        System.out.println("图的深度优先遍历：");
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        System.out.print(node.value);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (set.contains(next)) {
                    continue;
                }
                stack.add(cur);
                stack.add(next);
                set.add(next);
                System.out.print(next.value);
                break;
            }
        }
    }

}
