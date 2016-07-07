package com.balance;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**一致性hash算法测试
 * @author zhongyi
 *
 */
public class ConsistentHash {
	public static Map<String,Integer> serverWeigthMap=new HashMap<String,Integer>();
	
	protected static int vnodesPerNode = 128;
	
	protected static TreeMap<Long,String> nodes = new TreeMap<Long,String>();
	
	
	static{
		serverWeigthMap.put("192.168.0.1", 1);
		serverWeigthMap.put("192.168.0.2", 1);
		serverWeigthMap.put("192.168.0.3", 3);
		serverWeigthMap.put("192.168.0.4",5);
		serverWeigthMap.put("192.168.0.5", 5);
		serverWeigthMap.put("192.168.0.6", 1);
		serverWeigthMap.put("192.168.0.7", 2);
	}
	
	/**
	 * 虚拟化节点
	 */
	public static void consistentHashServers(){
		Set<String> keySet=serverWeigthMap.keySet();
		Iterator<String> it=keySet.iterator();
		
		while(it.hasNext()){
			String _case = it.next();
			for (int i = 0 ;i < vnodesPerNode ; i ++){
				Long _key = hash(_case + "vnode" + i);
				nodes.put(_key, _case);
			}
		}
		
	}
	
	public static String testConsistentHash(String key){
		SortedMap<Long,String> tail=nodes.tailMap(hash(key));
		if(null == tail || tail.size() == 0){
			return nodes.get(nodes.firstKey());
		}else{
			return tail.get(tail.firstKey());	
		}
		
		
	}
	
	
	
	/**
	 * MurMurHash算法实现
	 * @param key
	 * @return
	 */
	static protected Long hash(String key) {
		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0xc6a4a7935bd1e995L;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(
					ByteOrder.LITTLE_ENDIAN);
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
	}
}
