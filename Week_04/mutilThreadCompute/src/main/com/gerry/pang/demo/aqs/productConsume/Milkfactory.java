package com.gerry.pang.demo.aqs.productConsume;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
「请寻求最优解，不要简单的生产者-消费者模式」
有一个生产奶酪的厂家，每天需要生产100000份奶酪卖给超市，通过一辆货车发货，送货车每次送100份。
厂家有一个容量为1000份的冷库，用于奶酪保鲜，生产的奶酪需要先存放在冷库，运输车辆从冷库取货。
厂家有三条生产线，分别是牛奶供应生产线，发酵剂制作生产线，奶酪生产线。
生产每份奶酪需要2份牛奶和一份发酵剂。
请设计生产系统？
 * 
 * @author pangguowei
 * @since 2020年11月27日 
 */
public class Milkfactory {
	public final static int TARGET_MAX_SIZE = 10000;
	public final static int RAW_MATERIALS_MAX_SIZE = 100*2;
	public final static int SAVE_MAX_SIZE = 100;
	public final static int CAR_MAX_SIZE = 100;
	private volatile static int num;
	public final static ArrayBlockingQueue<FaXiao> faXiaoQueue = new ArrayBlockingQueue<>(RAW_MATERIALS_MAX_SIZE);
	public final static ArrayBlockingQueue<Milk> milkQueue = new ArrayBlockingQueue<>(RAW_MATERIALS_MAX_SIZE);
	public final static ArrayBlockingQueue<Yogurt> yogurtQueue = new ArrayBlockingQueue<>(SAVE_MAX_SIZE);
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		executorService.execute(new MilkProducer());
		executorService.execute(new FaXiaoProducer());
		executorService.execute(new YogurtProducer());
		executorService.execute(new Car());
		executorService.shutdown();
		
	}
	
	static class FaXiao {
		public FaXiao() {
			super();
		}
	}
	
	static class Milk {
		public Milk() {
			super();
		}
		
	}
	static class Yogurt {
		public Yogurt(FaXiao fx, Milk one, Milk two) {
			super();
			System.out.println("************ Yogurt ************ ");
		}
		
	}

	static class FaXiaoProducer implements Runnable {
		@Override
		public void run() {
			while(true && num < TARGET_MAX_SIZE) {
				try {
					faXiaoQueue.offer(new FaXiao(), 1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("==== stop FaXiaoProducer ===");
		}
	}
	
	static class MilkProducer implements Runnable {
		@Override
		public void run() {
			while(true && num < TARGET_MAX_SIZE) {
				try {
					milkQueue.offer(new Milk(), 1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("==== stop MilkProducer ===");
		}
	}

	static class YogurtProducer implements Runnable {
		@Override
		public void run() {
			try {
				while(num < TARGET_MAX_SIZE) {
					System.out.println("===> " + num);
					Milk one = milkQueue.take();
					Milk two = milkQueue.take();
					FaXiao fx = faXiaoQueue.take();
					if (one != null && two != null && fx != null) {
						yogurtQueue.put(new Yogurt(fx, one, two));
						num++;
					}
				}
				System.out.println("======= finish ======= ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class Car implements Runnable {
		private List<Yogurt> car = new ArrayList<>(CAR_MAX_SIZE);
		
		private int size = 0;
		
		private int round = 0;
		
		@Override
		public void run() {
			while(true) {
				try {
					if (size < CAR_MAX_SIZE) {
						Yogurt yogurt = yogurtQueue.take();
						car.add(yogurt);
						size++;
					} else {
						round++;
						System.out.println("@@@@@@@@@@@@@@ " + round);
						car.clear();
						size = 0;
						if (num == TARGET_MAX_SIZE) {
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("==== stop Car ===");
		}
	}
	
}
