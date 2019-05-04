package com.zoy.gupao.ch1.proxy.my.jdk.impl;


import com.zoy.gupao.ch1.proxy.jdk.Person;
import com.zoy.gupao.ch1.proxy.my.jdk.MyInvocationHandler;

import java.lang.reflect.Method;

public class MyMeipo implements MyInvocationHandler {

	private Person target;

	//获取被代理人的个人资料
	public Object getInstance(Person target) throws Exception{
		this.target = target;
		Class clazz = target.getClass();
		System.out.println("被代理对象的class是:"+clazz);
		return MyPorxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("我是媒婆：得给你找个异性才行");
		System.out.println("开始进行海选...");
		System.out.println("------------");
		method.invoke(this.target, args);
		System.out.println("------------");
		System.out.println("如果合适的话，就准备办事");
		return null;
	}

}
