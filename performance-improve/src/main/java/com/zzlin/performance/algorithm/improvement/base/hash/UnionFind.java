package com.zzlin.performance.algorithm.improvement.base.hash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 并查集
 *
 * @author zlin
 * @date 20230303
 */
public class UnionFind {

    /**
     * 将集合元素包装一层的结构
     *
     * @param <V> 集合元素
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Element<V> {
        private V value;
    }

    @Data
    private static class UnionFindSet<V> {

        /**
         * key: 集合值
         * val: 值所对应的包装元素
         */
        private Map<V, Element<V>> elementMap;

        /**
         * key: 某个元素
         * val: key元素的父元素
         */
        private Map<Element<V>, Element<V>> fatherMap;

        /**
         * key: 集合的代表元素
         * val: 集合的大小
         */
        private Map<Element<V>, Integer> sizeMap;

        public UnionFindSet(List<V> list) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : list) {
                Element<V> element = new Element<>(v);
                elementMap.put(v, element);
                fatherMap.put(element, element);
                sizeMap.put(element, 1);
            }
        }

        public boolean isSameSet(V a, V b) {
            if (!elementMap.containsKey(a) || !elementMap.containsKey(b)) {
                return false;
            }
            return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
        }

        public void union(V a, V b) {
            if (!elementMap.containsKey(a) || !elementMap.containsKey(b)) {
                return;
            }
            Element<V> aF = findHead(elementMap.get(a));
            Element<V> bF = findHead(elementMap.get(b));
            if (aF == bF) {
                return;
            }
            Element<V> big;
            Element<V> small;
            if (sizeMap.get(aF) >= sizeMap.get(bF)) {
                big = aF;
                small = bF;
            } else {
                big = bF;
                small = aF;
            }
            fatherMap.put(small, big);
            sizeMap.put(big, sizeMap.get(aF) + sizeMap.get(bF));
            // small不再是代表结点了，big是两者的共有节点
            sizeMap.remove(small);
        }

        /**
         * 找到给定element的代表节点
         * 一直找父级节点，把最终的代表节点返回
         * 将期间经过的父节点不为代表节点的扁平化，直接指向最终的代表节点
         *
         * 经过证明：该方法在调用频繁的情况下时间复杂度为O(1)
         *
         * @param element 指定的节点
         * @return 代表节点
         */
        private Element<V> findHead(Element<V> element) {
            Stack<Element<V>> stack = new Stack<>();
            while (element != fatherMap.get(element)) {
                // 若element不是自己的父节点，那么久还有父级节点
                stack.push(element);
                element = fatherMap.get(element);
            }
            while (!stack.isEmpty()) {
                // 所有代表节点不是自己的节点，都归话到最上一级的代表节点
                fatherMap.put(stack.pop(), element);
            }
            return element;
        }

    }

}
