package jdk;

import com.proxy.jdk.Subject;
import com.proxy.jdk.SubjectImp;
import com.proxy.jdk.SubjectProxy;

public class JdkProxyTest {
	public static void main(String[] args) {
		SubjectProxy proxy=new SubjectProxy();
		Subject s=(Subject)proxy.bind(new SubjectImp());
		s.request(1);
	}
}
