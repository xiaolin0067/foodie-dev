package com.zzlin.performance.algorithm.hot;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 单例模式的5种写法
 *
 * @author pang
 * @date 2024/7/17
 */
public class SingletonPatternTest {

    static class SingletonPattern1 {
        private static final SingletonPattern1 INSTANCE = new SingletonPattern1();
        private SingletonPattern1() {
        }
        public static SingletonPattern1 getInstance() {
            return INSTANCE;
        }
        public void test() {
        }
    }

    static class SingletonPattern2 {
        private volatile static SingletonPattern2 INSTANCE;
        private SingletonPattern2() {
        }

        public static SingletonPattern2 getInstance() {
            if (INSTANCE == null) {
                synchronized (SingletonPattern2.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new SingletonPattern2();
                    }
                }
            }
            return INSTANCE;
        }
        public void test() {
        }
    }

    enum SingletonPattern3 {
        INSTANCE;

        public void test() {
        }
    }

    static class SingletonPattern4 {
        private static class InstanceHolder {
            private static final SingletonPattern4 INSTANCE = new SingletonPattern4();
        }
        private SingletonPattern4() {
        }
        public static SingletonPattern4 getInstance() {
            return InstanceHolder.INSTANCE;
        }
        public void test() {
        }
    }

    static class SingletonPattern5 {
        private static final AtomicReference<SingletonPattern5> INSTANCE = new AtomicReference<>();
        private SingletonPattern5() {
        }

        public static SingletonPattern5 getInstance() {
            if (INSTANCE.get() != null) {
                return INSTANCE.get();
            }
            for (;;) {
                if (INSTANCE.get() != null) {
                    return INSTANCE.get();
                }
                INSTANCE.compareAndSet(null, new SingletonPattern5());
            }
        }

    }
    
}

