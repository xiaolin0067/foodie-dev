package com.zzlin.performance.algorithm.tree;

/**
 * todo 修改描述信息
 *
 * @author pang
 * @date 2024/8/20 03:13
 * @since V3.17.0
 */
public class TireTree {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));;
    }

   static class Trie {

        Node root;

        class Node {
            // 经过当前节点的数量
            int pass;
            // 以当前节点为末尾节点的数量
            int tail;
            // 下一个节点
            Node[] nexts = new Node[26];
        }

        public Trie() {
            this.root = new Node();
        }

        public void insert(String word) {
            if (word == null || word.length() < 1) {
                return;
            }
            Node cur = root;
            cur.pass++;
            for (char c : word.toCharArray()) {
                int cIdx = c - 'a';
                if (cur.nexts[cIdx] == null) {
                    cur.nexts[cIdx] = new Node();
                }
                cur = cur.nexts[cIdx];
                cur.pass++;
            }
            cur.tail++;
        }

        public boolean search(String word) {
            if (word == null || word.length() < 1) {
                return false;
            }
            Node cur = root;
            for (char c : word.toCharArray()) {
                cur = cur.nexts[c - 'a'];
                if (cur == null) {
                    return false;
                }
            }
            return cur.tail > 0;
        }

        public boolean startsWith(String prefix) {
            if (prefix == null || prefix.length() < 1) {
                return false;
            }
            Node cur = root;
            for (char c : prefix.toCharArray()) {
                cur = cur.nexts[c - 'a'];
                if (cur == null) {
                    return false;
                }
            }
            return true;
        }
    }
}
