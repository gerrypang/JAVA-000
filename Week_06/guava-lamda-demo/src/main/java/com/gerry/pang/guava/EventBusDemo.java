package com.gerry.pang.guava;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusDemo {
	
	static class CustomEvent {
		private int age;

		public CustomEvent(int age) {
			super();
			this.age = age;
		}

		public int getAge() {
			return age;
		}
	}

	 
	// 一个类中可以有多个订阅者
	static class EventListener1 {
		@Subscribe
		public void test1(CustomEvent event) {
			System.out.println(Instant.now() +"监听者1-->订阅者1,收到事件："+event.getAge()+"，线程号为："+Thread.currentThread().getName());
			try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			System.out.println(Instant.now() +"监听者1 end");
		}

		@Subscribe
		public void test11(CustomEvent event) {
			System.out.println(Instant.now() +"监听者11-->订阅者11,收到事件："+event.getAge()+"，线程号为："+Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Instant.now() +"监听者11 end");
		}
		
	}

	static class EventListener2 {
		@Subscribe
		public void test2(CustomEvent event) {
			System.out.println(Instant.now() +"监听者2-->订阅者2,收到事件："+event.getAge()+"，线程号为："+Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Instant.now() +"监听者2 end");
		}
	}

	static class DeadEventListener {
		@Subscribe
		public void test2(DeadEvent event) {
			System.out.println(Instant.now() +"Dead监听者-->订阅者,收到事件："+event.getEvent()+"，线程号为："+Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Instant.now() +"Dead监听者 end");
		}
	}
	
	public static void main(String[] args) {
//		syncEventBus();
		
//		asyncEventBus();
		
		deadEvent();
	}

	private static void asyncEventBus() {
		EventListener1 listener1 = new EventListener1();
        EventListener2 listener2 = new EventListener2();
        CustomEvent customEvent = new CustomEvent(23);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        AsyncEventBus asyncEventBus = new AsyncEventBus(executor);
        
        asyncEventBus.register(listener2);
        asyncEventBus.register(listener1);
        
        // 监听对象同时获取事件通知
        asyncEventBus.post(customEvent);
        
        executor.shutdown();
//        2020-11-26T07:36:43.807Z监听者2-->订阅者2,收到事件：23，线程号为：pool-1-thread-1
//        2020-11-26T07:36:43.807Z监听者11-->订阅者11,收到事件：23，线程号为：pool-1-thread-3
//        2020-11-26T07:36:43.807Z监听者1-->订阅者1,收到事件：23，线程号为：pool-1-thread-2
//        2020-11-26T07:36:46.875Z监听者11 end
//        2020-11-26T07:36:46.875Z监听者2 end
//        2020-11-26T07:36:46.875Z监听者1 end
        
	}

	private static void syncEventBus() {
		EventListener1 listener1 = new EventListener1();
        EventListener2 listener2 = new EventListener2();
        CustomEvent customEvent = new CustomEvent(23);
        EventBus eventBus = new EventBus();
        eventBus.register(listener1);
        eventBus.register(listener2);
        
        // 一个接着一个的通知
        eventBus.post(customEvent);
//        2020-11-26T07:27:08.066Z监听者1-->订阅者1,收到事件：23，线程号为：main
//        2020-11-26T07:27:11.204Z监听者1 end
//        2020-11-26T07:27:11.204Z监听者2-->订阅者2,收到事件：23，线程号为：main
//        2020-11-26T07:27:14.204Z监听者2 end
	}

	private static void deadEvent() {
		DeadEventListener deadlistener1 = new DeadEventListener();
		CustomEvent customEvent = new CustomEvent(23);
		EventBus eventBus = new EventBus();
		eventBus.register(deadlistener1);
		
		// 如果没有消息订阅者监听消息， EventBus将发送DeadEvent消息
		eventBus.post(customEvent);
	}
}
