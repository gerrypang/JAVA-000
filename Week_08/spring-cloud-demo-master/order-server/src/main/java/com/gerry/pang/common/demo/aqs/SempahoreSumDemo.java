package com.gerry.pang.common.demo.aqs;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SempahoreSumDemo {
    private static final int NUM = 4;
    private static final List<Integer> resultList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(0);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(100);
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);

        try {
            int sum = 0;
            for (int i = 0; i < NUM; i++) {
                executor.submit(new Runner(semaphore));
            }

            System.out.println("1=============");
            semaphore.acquire(4);
            System.out.println("2=============");
            for (Integer one : resultList) {
                sum += one.intValue();
            }
            System.out.println(sum);

        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    static class Runner implements Runnable {

        private Semaphore semaphore;

        public Runner(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "start");
            int a = new Random().nextInt(10);
            System.out.println(Thread.currentThread().getName() + " is " + a);
            resultList.add(a);
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + "end");
        }
    }
}
