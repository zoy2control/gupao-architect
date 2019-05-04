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
 * 生成代理对象的代码
 */
public class MyPorxy {

	private static String ln = "\r\n";

	public static Object newProxyInstance(MyClassLoader classLoader,Class<?>[] interfaces,MyInvocationHandler h){


		try{
			//1、生成源代码
			String proxySrc = generateSrc(interfaces[0]);


			//2、将生成的源代码输出到磁盘，保存为.java文件
			String filePath = MyPorxy.class.getResource("").getPath();
			File f = new File(filePath + "$Proxy0.java");
			FileWriter fw = new FileWriter(f);
			fw.write(proxySrc);
			fw.flush();
			fw.close();

			//3、编译源代码，并且生成.class文件
			JavaCompiler  compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
			Iterable iterable = manager.getJavaFileObjects(f);

			CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
			task.call();
			manager.close();

			//4、将class文件中的内容，动态加载到JVM中来

			//5、返回被代理后的代理对象
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
	 * 这个拼接的代码，就是我们前面 反编译生成的 $Proxy0.class里面的 文件内容
	 * @param interfaces
	 * @return
	 */
	private static String generateSrc(Class<?> interfaces){
		StringBuffer src = new StringBuffer();
		src.append("com.zoy.gupao.proxy.my.jdk.impl;" + ln);
		src.append("import java.lang.reflect.Method;" + ln);
		src.append("public class $Proxy0 implements " + interfaces.getName() + "{" + ln);// 类

		src.append("MyInvocationHandler h;" + ln);// 成员变量

		// 构造器
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
