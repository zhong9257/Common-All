package com.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

interface PersistenceService{
	
}

/** 
 * A simple implementation of PersistenceService interface 
 */  
class PersistenceServiceImpl implements PersistenceService {  
  
    //需要权限检查  
    public void save(long id, String data) {  
        System.out.println(data + " has been saved successfully.");  
    }  
  
    //不需要权限检查  
    public String load(long id) {  
        return "Test CGLIB CallBackFilter";  
    }  
}  
  
/** 
 * An implementation of CallbackFilter for PersistenceServiceImpl 
 */  
public class PersistenceServiceCallbackFilter implements CallbackFilter {   
    //callback index for save method  
    private static final int SAVE = 0;  
    //callback index for load method  
    private static final int LOAD = 1;  
  
    /** 
     * Specify which callback to use for the method being invoked.  
     * @param method the method being invoked. 
     * @return  
     */  
    @Override  
    public int accept(Method method) {  
        //指定各方法的代理回调索引  
        String name = method.getName();  
        if ("save".equals(name)) {  
            return SAVE;  
        }  
        // for other methods, including the load method, use the  
        // second callback  
        return LOAD;  
    }
    
    public static void main(String[] args) {
    	Enhancer enhancer = new Enhancer();
    	enhancer.setSuperclass(PersistenceServiceImpl.class);
    	//设置回调过滤器
    	CallbackFilter callbackFilter = new PersistenceServiceCallbackFilter();
    	enhancer.setCallbackFilter(callbackFilter);
    	//创建各个目标方法的代理回调

    	Callback saveCallback = new MethodInterceptorProxy();
    	Callback loadCallback = NoOp.INSTANCE;
    	//顺序要与指定的回调索引一致
    	Callback[] callbacks = new Callback[]{saveCallback, loadCallback };
    	enhancer.setCallbacks(callbacks);  //设置回调
    	PersistenceServiceImpl o=(PersistenceServiceImpl)enhancer.create(); //创建代理对象
    	
    	o.save(1, "123");
    	o.load(1);
	}
    
}  