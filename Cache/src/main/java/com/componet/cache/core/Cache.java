package com.componet.cache.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**单机版jvm缓存
 * @author zhongyi
 *
 */
public class Cache {
	/**
	 * 考虑到缓存写的操作不频繁和简单实现，就不适用ConcurrentHashMap的锁分段了。
	 */
	private static final Map<String, Object> map = new HashMap<String, Object>();
	/**
	 * 读写锁。读共享锁；写独占锁；
	 */
	private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static final Lock r = rwl.readLock();
	private static final Lock w = rwl.writeLock();
	
	/**
	 * schedule按计划去执行key的失效
	 */
	private static final ScheduledExecutorService exprieExecutor= Executors.newScheduledThreadPool(2, new DefualtThreadFactory());
	/**
	 * 记录待失效key
	 */
	private static final ConcurrentHashMap<String, Future> exprieMap=new ConcurrentHashMap<String, Future>();
	

	
	
	/**根据key取缓存
	 * @param key
	 * @return
	 */
	public static final Object get(String key) {
		r.lock();
		try {
			return map.get(key);
		} finally {
			r.unlock();
		}
	}

	/**添加内容到缓存，永远不失效
	 * @param key
	 * @param value
	 * @return
	 */
	public static final Object put(String key, String value) {
		w.lock();
		try {
			removeExprieSetting(key);
			return map.put(key, value);
		} finally {
			w.unlock();
		}
	}

	/**添加内容到缓存且设置了失效时间
	 * @param key
	 * @param value
	 * @param exprie 失效时间，单位秒
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Object put(String key, String value, Long exprie) {
		w.lock();
		try {
			
			removeExprieSetting(key);
			ExprieObject e =new ExprieObject(key);
			Future future=exprieExecutor.schedule(e, exprie, TimeUnit.SECONDS);
			exprieMap.put(key, future);
			return map.put(key, value);
		} finally {
			w.unlock();
		}
	}
	
	
	/**删除缓存
	 * @param key
	 * @return
	 */
	public static final Object remove(String key){
		w.lock();
		try {		
			return map.remove(key);
		} finally {
			removeExprieSetting(key);
			w.unlock();
		}
	}

	
	/**清楚缓存对象的失效设置
	 * @param key
	 */
	public static final void removeExprieSetting(String key){
		Future future=exprieMap.remove(key);
		if(null!=future){
			if(future.isCancelled()||future.isDone()){}
			future.cancel(true);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Cache.put("key1", "key1");
		Cache.put("key2", "key2");
		Cache.put("key3", "key3",10l);
		Cache.put("key4", "key4",30l);
		Thread.sleep(20000);
		System.out.println(Cache.get("key2"));
		System.out.println(Cache.get("key3"));
		System.out.println(Cache.get("key1"));
		Cache.put("key4", "key4");
		Thread.sleep(60000);
		System.out.println(Cache.get("key4"));
	}
	
	
	
	
	private static class DefualtThreadFactory implements ThreadFactory{
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("Cache-Exprie-Thread");
			return t;
		}

	}
	

	

	private static class ExprieObject<V> implements Callable<V> {
		private String key;	
		public ExprieObject(String key) {
			super();
			this.key = key;
		}
		@Override
		public V call() throws Exception {
			map.remove(key);
			return null;
		}
	}
}
