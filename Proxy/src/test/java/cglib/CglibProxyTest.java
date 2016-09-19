package cglib;

import com.proxy.cglib.MethodInterceptorProxy;
import com.proxy.cglib.SubjectNoIf;

public class CglibProxyTest {

	public static void main(String[] args) {
		MethodInterceptorProxy cglibProxy = new MethodInterceptorProxy();
		SubjectNoIf hw = (SubjectNoIf) cglibProxy.createProxy(new SubjectNoIf());
		hw.request(1);
	}

}
