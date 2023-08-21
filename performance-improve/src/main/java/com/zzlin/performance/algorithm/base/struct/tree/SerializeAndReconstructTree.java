package com.zzlin.performance.algorithm.base.struct.tree;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 序列化与反序列化树
 * @author zlin
 * @date 20220716
 */
public class SerializeAndReconstructTree {

    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;
        System.out.println(serializeByPre(n1));
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node(int val) {
            this.val = val;
        }
    }

    public static String serializeByPre(Node root) {
        if (root == null) {
            return "#,";
        }
        String res = root.val + ",";
        res += serializeByPre(root.left);
        res += serializeByPre(root.right);
        return res;
    }

    public static Node reconByPreString(String serialize) {
        if (StringUtils.isBlank(serialize)) {
            return null;
        }
        Queue<String> queue = new LinkedList<>(Arrays.asList(serialize.split(",")));
        return reconPreOrder(queue);
    }

    private static Node reconPreOrder(Queue<String> queue) {
        String val = queue.poll();
        if ("#".equals(val)) {
            return null;
        }
        Node head = new Node(Integer.parseInt(val));
        head.left = reconPreOrder(queue);
        head.right = reconPreOrder(queue);
        return head;
    }

}
