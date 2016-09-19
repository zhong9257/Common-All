package com.proxy.cglib;

import net.sf.cglib.proxy.Mixin;

interface MyA{
	void methodA();
}

interface MyB{
	void methodB();
}

class MyAImpl implements MyA{

	@Override
	public void methodA() {
		System.out.println(" method A");
	}
	
}

class MyBImpl implements MyB{

	@Override
	public void methodB() {
		System.out.println(" method B");
	}
	
}

/**Mixin通过代理方式将多种类型的对象绑定到一个大对象上，这样对各个目标类型中的方法调用可以直接在这个大对象上进行
 * @author zhongyi
 *
 */
public class CglibMixin {
	public static void main(String[] args) {
		Class[] interfaces = new Class[]{MyA.class,MyB.class};
		Object[] delegates=new Object[]{new MyAImpl(),new MyBImpl()};
		
		Object o=Mixin.create(interfaces, delegates);
		((MyA)o).methodA();
		((MyB)o).methodB();
	}
}
