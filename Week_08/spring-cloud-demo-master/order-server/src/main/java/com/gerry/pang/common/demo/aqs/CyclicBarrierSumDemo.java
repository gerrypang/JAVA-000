package com.gerry.pang.common.demo.aqs;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class CyclicBarrierSumDemo {

    private static final int NUM = 4;
    private static final List<Integer> resultList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(100);
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 1000, TimeUnit.SECONDS, workQueue);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM, new Runnable() {
            @Override
            public void run() {
                if (CollectionUtils.isEmpty(resultList)){
                    System.out.println("==== all start run ====");
                    return;
                }
                int sum = 0;
                for (Integer one : resultList) {
                    sum += one.intValue();
                }
                System.out.println(sum);
            }
        });
        try {
            for (int i = 0; i < NUM; i++) {
                executor.submit(new Runner(cyclicBarrier, i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    static class Runner implements Runnable {
        private CyclicBarrier cyclicBarrier  = null;
        private int num = -1;

        public Runner(CyclicBarrier cyclicBarrier, int num) {
            super();
            this.num = num;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                int a = new Random().nextInt(10);
                System.out.println("num:" + num + " is " + a);
                resultList.add(a);
                cyclicBarrier.await(); // 注意是 await 不是 wait
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}