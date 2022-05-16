package com.zzlin.performance.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author zlin
 * @date 20220515
 */
public class JolTest {

    static class T {
//        int a;
        long b;
    }

    public static void main(String[] args) {
        T obj = new T();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        int[] arr = new int[10];
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
    }

}
