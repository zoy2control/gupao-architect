package com.zoy.gupao.ch1.proxy.my.jdk;

import java.lang.reflect.Method;

/**
 * Created by zoypong on 2019/5/2.
 */
public interface MyInvocationHandler {
    // copy InvocationHandler的 invoke()方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
