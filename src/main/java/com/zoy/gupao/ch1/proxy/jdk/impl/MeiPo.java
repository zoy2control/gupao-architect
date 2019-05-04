package com.zoy.gupao.ch1.proxy.jdk.impl;

import com.zoy.gupao.proxy.jdk.Person;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理者：媒婆
 * 先是 实现InvocationHandler接口
 * 代理原理：
 * 1、拿到 被代理者的引用，然后获取它的接口
 *  如 Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this)中，clazz.getInterface()就是一个获取接口的过程
 * 2、JDK代理 重新生成一个新类，同时 实现我们给代理对象所实现的接口
 * 3、把 被代理的对象引用也拿到了
 * 4、重新动态生成一个 class字节码
 * 5、然后编译
 * Created by zoypong on 2019/5/2.
 */
@Data
public class MeiPo implements InvocationHandler{

    // 被代理者的对象，暂存在 成员变量中
    private Person target;

    // 获取 被代理者的个人信息
    // 供外部调用（被 代理调用的）
    public Object getInstance(Person target) {
        this.target = target;
        Class clazz = target.getClass();
        System.out.println("被代理者的原生class是：" + clazz);
        // 生成代理
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是媒婆，得给你找个异性才行：");
        System.out.println("=======================");
        this.target.findLove();
//        method.invoke(this.target, args);// 可以用这种方式，这个 Method可以通过 MeipoTest中的 获取字节码内容，然后反编译来分析
        System.out.println("=======================");
        System.out.println("如何合适的话，就准备牵线啦");

        return null;
    }
}
