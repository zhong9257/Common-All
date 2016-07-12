package com.balance;

import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
	public static ReentrantLock lock = new ReentrantLock();
	
	
	public static void main(String[] args) {
		try {
			lock.tryLock();
		} finally {
			lock.unlock();
		}
	}
}
