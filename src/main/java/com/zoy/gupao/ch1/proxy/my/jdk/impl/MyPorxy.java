package com.zoy.gupao.ch1.proxy.my.jdk.impl;


import com.zoy.gupao.ch1.proxy.my.jdk.MyInvocationHandler;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * ���ɴ�������Ĵ���
 */
public class MyPorxy {

	private static String ln = "\r\n";

	public static Object newProxyInstance(MyClassLoader classLoader,Class<?>[] interfaces,MyInvocationHandler h){


		try{
			//1������Դ����
			String proxySrc = generateSrc(interfaces[0]);


			//2�������ɵ�Դ������������̣�����Ϊ.java�ļ�
			String filePath = MyPorxy.class.getResource("").getPath();
			File f = new File(filePath + "$Proxy0.java");
			FileWriter fw = new FileWriter(f);
			fw.write(proxySrc);
			fw.flush();
			fw.close();

			//3������Դ���룬��������.class�ļ�
			JavaCompiler  compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
			Iterable iterable = manager.getJavaFileObjects(f);

			CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
			task.call();
			manager.close();

			//4����class�ļ��е����ݣ���̬���ص�JVM����

			//5�����ر�������Ĵ�������
			Class proxyClass = classLoader.findClass("$Proxy0");
			Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
			f.delete();

			return c.newInstance(h);

		}catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}


	/**
	 * ���ƴ�ӵĴ��룬��������ǰ�� ���������ɵ� $Proxy0.class����� �ļ�����
	 * @param interfaces
	 * @return
	 */
	private static String generateSrc(Class<?> interfaces){
		StringBuffer src = new StringBuffer();
		src.append("com.zoy.gupao.proxy.my.jdk.impl;" + ln);
		src.append("import java.lang.reflect.Method;" + ln);
		src.append("public class $Proxy0 implements " + interfaces.getName() + "{" + ln);// ��

		src.append("MyInvocationHandler h;" + ln);// ��Ա����

		// ������
		src.append("public $Proxy0(MyInvocationHandler h) {" + ln);
		src.append("this.h = h;" + ln);
		src.append("}" + ln);

		for (Method m : interfaces.getMethods()) {
			src.append("public " + m.getReturnType().getName() + " " + m.getName() + "(){" + ln);
			src.append("try{" + ln);
			src.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" +m.getName()+"\",new Class[]{});" + ln);
			src.append("this.h.invoke(this,m,null);" + ln);
			src.append("}catch(Throwable e){e.printStackTrace();}" + ln);
			src.append("}" + ln);
		}

		src.append("}");

		return src.toString();
	}
}