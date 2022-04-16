package com.zzlin.performance.area;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 * @author zlin
 * @date 20220415
 */
public class MethodAreaOomTest2 {
    /**
     * CGLib：https://blog.csdn.net/yaomingyang/article/details/82762697
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Hello.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    System.out.println("Enhanced hello");
                    // 调用Hello.say()
                    return proxy.invokeSuper(obj, args);
                }
            });
            Hello enhancedOOMObject = (Hello) enhancer.create();
            enhancedOOMObject.say();
            // 类定义信息放在元空间，故会导致元空间内存溢出
            System.out.println(enhancedOOMObject.getClass().getName());
        }
    }
}
class Hello {
    public void say() {
        System.out.println("Hello Student");
    }
}
