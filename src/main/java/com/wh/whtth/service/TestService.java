package com.wh.whtth.service;

import com.wh.whtth.thread.TestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2017/9/7.
 */
@Service("testService")
public class TestService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void test(Long id){
        ExecutorService pool = Executors.newFixedThreadPool(10);
        CountDownLatch begin = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            pool.execute(new TestRunner(this,id,begin));
        }
        begin.countDown();
    }

    public Object set(){
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.watch("test");
                redisOperations.multi();
                redisOperations.opsForValue().increment("test",1);
                List exec = redisOperations.exec();
                return exec;
            }
        };
        return redisTemplate.execute(sessionCallback);
    }
}
