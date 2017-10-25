package com.qiyuan.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDateUtil {

	static Map<String, Object> dataMap = new HashMap<String, Object>();
	static ReadWriteLock lock = new ReentrantReadWriteLock();// 创建读写锁的实例

	static Map<String, Object> compTime(String a,long time){
		Long object = (Long) dataMap.get(a);
		if(object==null){
			dataMap.put(a, time);
			dataMap.put("1", "meiyou");
			return dataMap;
		}else{
			long timeDifference = time-object;
			if(timeDifference<700){
				dataMap.put(a, time);
				dataMap.put("2", "buzu");
				return dataMap;
			}else{
				dataMap.put(a, time);
				dataMap.put("3", "chaoguo");
				return dataMap;
			}
		}
	}
	
	
	static Object getData(Integer key) {
		lock.readLock().lock();// 读取前先上锁
		Object val = null;
		try {
			val = dataMap.get(key);
			if (val == null) {
				// Must release read lock before acquiring write lock
				lock.readLock().unlock();
				lock.writeLock().lock();
				try {
					// 可能已经由其他线程写入数据
					if (val == null) {
						// dataMap.put(key, "");//query from db
						val = queryDataFromDB(key);
					}
				} finally {
					// Downgrade by acquiring read lock before releasing write
					// lock
					lock.readLock().lock();
					// Unlock write, still hold read
					lock.writeLock().unlock();
				}
			}
		} finally {
			lock.readLock().unlock();// 最后一定不要忘记释放锁
		}
		System.out.println("get data key=" + key + ">val=" + val);
		return val;
	}

	static Object queryDataFromDB(Integer key) {
		Object val = new Random().nextInt(1000);
//		dataMap.put(key, val);
		System.out.println("write into data key=" + key + ">val=" + val);
		return val;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					getData(new Random().nextInt(5));
				}
			}).start();
		}
	}

}
