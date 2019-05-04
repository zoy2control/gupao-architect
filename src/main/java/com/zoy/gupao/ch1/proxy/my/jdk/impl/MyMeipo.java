package com.zoy.gupao.ch1.proxy.my.jdk.impl;


import com.zoy.gupao.ch1.proxy.jdk.Person;
import com.zoy.gupao.ch1.proxy.my.jdk.MyInvocationHandler;

import java.lang.reflect.Method;

public class MyMeipo implements MyInvocationHandler {

	private Person target;

	//��ȡ�������˵ĸ�������
	public Object getInstance(Person target) throws Exception{
		this.target = target;
		Class clazz = target.getClass();
		System.out.println("�����������class��:"+clazz);
		return MyPorxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("����ý�ţ��ø����Ҹ����Բ���");
		System.out.println("��ʼ���к�ѡ...");
		System.out.println("------------");
		method.invoke(this.target, args);
		System.out.println("------------");
		System.out.println("������ʵĻ�����׼������");
		return null;
	}

}