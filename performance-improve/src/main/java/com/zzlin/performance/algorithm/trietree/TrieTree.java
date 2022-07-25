package com.zzlin.performance.algorithm.trietree;

import org.apache.commons.lang3.StringUtils;

/**
 * 前缀树
 *
 * @author zlin
 * @date 20220725
 */
public class TrieTree {

    public static class TrieNode {
        /**
         * 当前节点通过的次数
         * 所有字符串都经过根节点，根节点的此值代表加入了多少个字符串
         */
        public int pass;
        /**
         * 当前节点作为尾节点的次数
         */
        public int end;
        /**
         * 走向下一个字符 'a'~'z' 的路，若为null即后面没有这个字符的路
         * nexts[0]即有走向'a'字符的路
         * nexts[25]即有走向'z'字符的路
         * 如果字符串中的字符种类特别的多，可以换成map数据类型
         * HashMap<Char,Node> nexts;
         * TreeMap<Char, Node> nexts;
         */
        public TrieNode[] nexts;

        public TrieNode() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new TrieNode[26];
        }
    }

    public static class Trie {

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * 前缀树插入字符串
         */
        public void insert(String word) {
            // todo 校验 a~z
            if (StringUtils.isBlank(word)) {
                return;
            }
            char[] chs = word.toCharArray();
            TrieNode head = root;
            head.pass++;
            int index;
            // 从左往右遍历字符
            for (char ch : chs) {
                index = ch - 'a';
                if (head.nexts[index] == null) {
                    head.nexts[index] = new TrieNode();
                }
                head = head.nexts[index];
                head.pass++;
            }
            head.end++;
        }

        /**
         * 从前缀树中删除字符串
         * 先判断字符串是否存在
         * 依次节点pass--，若节点pass=0了，将节点置为null，最后字符节点end--
         */
        public void delete(String word) {
            if (StringUtils.isBlank(word) || search(word) < 1) {
                return;
            }
            char[] chs = word.toCharArray();
            TrieNode head = root;
            head.pass--;
            int index;
            for (char ch : chs) {
                index = ch - 'a';
                // 若节点pass=0了，将节点置为null
                if (--head.nexts[index].pass == 0) {
                    head.nexts[index] = null;
                    return;
                }
                head = head.nexts[index];
                head.pass++;
            }
            head.end--;
        }

        /**
         * 前缀树搜索字符串完整的出现了几次
         * @return 出现次数
         */
        public int search(String word) {
            if (StringUtils.isBlank(word) || root.pass < 1) {
                return 0;
            }
            char[] chs = word.toCharArray();
            TrieNode head = root;
            for (char ch : chs) {
                head = head.nexts[ch - 'a'];
                if (head == null) {
                    return 0;
                }
            }
            return head.end;
        }

        /**
         * 前缀树搜索字符串作为前缀的字符串个数
         * @return 出现次数
         */
        public int prefixNumber(String pre) {
            if (StringUtils.isBlank(pre) || root.pass < 1) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            TrieNode head = root;
            for (char ch : chs) {
                head = head.nexts[ch - 'a'];
                if (head == null) {
                    return 0;
                }
            }
            return head.pass;
        }



    }

}
