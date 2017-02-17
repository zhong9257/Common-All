package com.me.common.file.lock.demo.test;

import com.me.common.file.lock.demo.WriteFileThread;

public class MultiWriteThread {
	public static void main(String[] args) {
		WriteFileThread w1=new WriteFileThread();
		w1.setName("w1");
		WriteFileThread w2=new WriteFileThread();
		w2.setName("w2");
		w1.start();
		w2.start();
	}
}
