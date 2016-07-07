package com.balance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**常见的均衡算法test（轮询、随机、hash、加权轮询、加权随机）
 * @author zhongyi
 *
 */
public class Balance {
	public static Map<String,Integer> serverWeigthMap=new HashMap<String,Integer>();
	public static Integer pos=0;
	
	static{
		serverWeigthMap.put("192.168.0.1", 1);
		serverWeigthMap.put("192.168.0.2", 1);
		serverWeigthMap.put("192.168.0.3", 3);
		serverWeigthMap.put("192.168.0.4",5);
		serverWeigthMap.put("192.168.0.5", 5);
		serverWeigthMap.put("192.168.0.6", 1);
		serverWeigthMap.put("192.168.0.7", 2);
	}
	
	public static String testRoundRobin(){
		
		//重新建一个map，避免应用上下线导致下标越界
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverWeigthMap);
		
		Set<String> keySet=serverMap.keySet();
		ArrayList<String> keyList=new ArrayList<>();
		keyList.addAll(keySet);
		
		String server=null;
		synchronized(pos){
			pos++;
			if(pos>keyList.size())
				pos=0;
			server=keyList.get(pos);
		}
		return server;
	}
	
	
	public static String testRandom(){
		//重新建一个map，避免应用上下线导致下标越界
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverWeigthMap);
		
		Set<String> keySet=serverMap.keySet();
		ArrayList<String> keyList=new ArrayList<>();
		keyList.addAll(keySet);
		
		Random r= new Random();
		return keyList.get(r.nextInt(keyList.size()));
	}
	
	public static String testConsumerHash(String remoteIp){
		//重新建一个map，避免应用上下线导致下标越界
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverWeigthMap);
		
		Set<String> keySet=serverMap.keySet();
		ArrayList<String> keyList=new ArrayList<>();
		keyList.addAll(keySet);
		int serverPos=remoteIp.hashCode()&Integer.MAX_VALUE%keyList.size();
		return keyList.get(serverPos);
	}
	
	
	
	/**加权轮询
	 * @return
	 */
	public static String testWeightRoundRobin(){
		
		//重新建一个map，避免应用上下线导致下标越界
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverWeigthMap);
		
		Set<String> keySet=serverMap.keySet();
		Iterator<String> it=keySet.iterator();
		
		ArrayList<String> serverList=new ArrayList<>();
		while(it.hasNext()){
			String server=it.next();
			Integer weight=serverMap.get(server);
			for (int i = 0; i < weight; i++) {
				serverList.add(server);
			}
			
		}
			
		
		String server=null;
		synchronized(pos){
			pos++;
			if(pos>serverList.size())
				pos=0;
			server=serverList.get(pos);
		}
		return server;
	}
	
	
	public static String testWeightRandom(){
		//重新建一个map，避免应用上下线导致下标越界
		Map<String,Integer> serverMap=new HashMap<String,Integer>();
		serverMap.putAll(serverWeigthMap);
		
		Set<String> keySet=serverMap.keySet();
		Iterator<String> it=keySet.iterator();
		
		ArrayList<String> serverList=new ArrayList<>();
		while(it.hasNext()){
			String server=it.next();
			Integer weight=serverMap.get(server);
			for (int i = 0; i < weight; i++) {
				serverList.add(server);
			}
			
		}
		
		Random r= new Random();
		return serverList.get(r.nextInt(serverList.size()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
