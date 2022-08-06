package com.zzlin.performance.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * 使用CGLIB实现动态代理
 * @author zlin
 * @date 20220806
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        // 使用cglib实现动态代理
        System.out.println("---------------cglib动态代理------------------");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Chinese.class);
        enhancer.setUseCache(false);
        enhancer.setCallback((MethodInterceptor) (obj, method, methodArgs, methodProxy) -> {
            System.out.println("代理执行前，before");
            // 调用Chinese.say()
            Object result = methodProxy.invokeSuper(obj, methodArgs);
            System.out.println("代理执行后，after");
            return result;
        });
        Chinese chineseProxy = (Chinese) enhancer.create();
        chineseProxy.say();
    }

}

