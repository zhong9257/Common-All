package com.proxy.jdk;

public class SubjectImp implements Subject {

	@Override
	public Integer request(Integer in) {
		System.out.println("SubjectImp is excute");
		return null;
	}

}
