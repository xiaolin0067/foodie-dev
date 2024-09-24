package com.zzlin.performance.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 临时测试
 *
 * @author pang
 * @date 2024/8/25 14:00
 * @since V3.17.0
 */
public class TmpTest {

    public static void main(String[] args) {
    }

    static class Solution {

        public Node cloneGraph(Node node) {
            if (node == null) {
                return node;
            }
            Map<Node, Node> map = new HashMap<>();
            Queue<Node> queue = new LinkedList<>();
            queue.add(node);
            Node newHead = new Node(node.val);
            map.put(node, newHead);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                Node curRepeat = map.get(cur);
                for (Node next : cur.neighbors) {
                    if (map.containsKey(cur)) {
                        continue;
                    }
                    Node newNext = new Node(next.val);
                    curRepeat.neighbors.add(newNext);
                    queue.add(next);
                    map.put(next, newNext);
                }
            }
            return newHead;
        }
    }

    static class Node {

        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
