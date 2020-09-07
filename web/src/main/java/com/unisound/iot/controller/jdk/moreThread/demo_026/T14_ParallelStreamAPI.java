package com.unisound.iot.controller.jdk.moreThread.demo_026;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class T14_ParallelStreamAPI {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		Random r = new Random();
		for(int i=0; i<10000; i++) list.add(1000000 + r.nextInt(1000000));
		
		//System.out.println(nums);
		
		long start = System.currentTimeMillis();
		list.forEach(v->isPrime(v));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		//使用parallel stream api
		
		start = System.currentTimeMillis();
		//因为isPrime 是静态方法，所以可以放在类引用后直接调用
		list.parallelStream().forEach(T14_ParallelStreamAPI::isPrime);
		end = System.currentTimeMillis();
		
		System.out.println(end - start);
	}
	
	static boolean isPrime(int num) {
		for(int i=2; i<= Math.sqrt(num); i++) {
			if(num % i == 0) return false;
		}
		return true;
	}
}
