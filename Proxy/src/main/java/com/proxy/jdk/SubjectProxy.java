package com.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SubjectProxy implements InvocationHandler{
	
	private Object target;
	
	
	/**绑定目标对象，并且返回一个代理类
	 * @param o
	 * @return
	 */
	public Object bind(Object o){
		this.target=o;
		//这里有缺陷，目标对象必须有接口；cglib解决了这个缺陷
		return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), this);
	}
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before");
		//执行方法
		Object result=method.invoke(this.target, args);
		System.out.println("after");
		return result;
	}

}
