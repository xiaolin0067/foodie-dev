package com.zzlin.performance.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理：
 *   1、对接口的实现类进行动态代理，创建一个代理对象，在代理对象调用原对象方法的前后增加自定义扩展逻辑
 *   2、调用代理对象的方法，执行自定义扩展逻辑
 * @author zlin
 * @date 20220806
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        Hello hello = new Chinese();
        // 不代理
        hello.say();

        // JDK动态代理
        System.out.println("---------------JDK动态代理------------------");
        // 创建一个代理对象，代理对象在执行原对象方法的前后加入自己的逻辑
        Hello helloProxy = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                // InvocationHandler
                (proxy, method, methodArgs) -> {
                    // 代理过程插入的其他操作
                    System.out.println("代理执行前，before");
                    // 被代理对象=hello
                    Object result = method.invoke(hello, methodArgs);
                    System.out.println("代理执行后，after");
                    return result;
                }
        );
        // 使用代理对象来调用方法，这样就能执行到代理对象自己的逻辑
        helloProxy.say();
    }

}

interface Hello {
    void say();
}

class Chinese implements Hello {

    @Override
    public void say() {
        System.out.println("中文，哈喽！");
    }
}

