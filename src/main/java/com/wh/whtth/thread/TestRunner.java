package com.wh.whtth.thread;

import com.wh.whtth.service.TestService;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/9/7.
 */
public class TestRunner implements Runnable {
    private CountDownLatch begin;
    private TestService testService;
    public TestRunner(TestService testService,Long id, CountDownLatch begin) {
        this.begin = begin;
        this.testService = testService;
    }

    @Override
    public void run() {
        try {
            begin.await();
            Object o = null;
            do {
                o = testService.set();
            }while (o == null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
