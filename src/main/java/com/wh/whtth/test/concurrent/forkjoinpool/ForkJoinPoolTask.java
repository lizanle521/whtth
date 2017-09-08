package com.wh.whtth.test.concurrent.forkjoinpool;

import java.util.concurrent.*;

/**
 * Created by Lizanle on 2017/9/8.
 */
public class ForkJoinPoolTask {
    static long sum = 0;
    //累加Task
     static class TestTask extends RecursiveTask<Long> {
        private long start;
        private long end;
        private long threshold;

        public TestTask(long start, long end, long threshold) {
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected Long compute() {

            if(end - start < threshold){

                System.out.println("start "+ start+" end  "+end);
                for (long i = start; i < end; i++) {
                    sum += i;
                }
            }else{
                long mid = (start + end )/2;
                TestTask testTask = new TestTask(start, mid, threshold);
                TestTask testTask1 = new TestTask(mid + 1, end, threshold);
                ForkJoinTask<Long> fork = testTask.fork();
                ForkJoinTask<Long> fork1 = testTask1.fork();
                sum = fork.join()+fork1.join();
            }
            return sum;
        }
    }

    // 替换Task
    class TestAction extends RecursiveAction {
        @Override
        protected void compute() {

        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinPool pool = new ForkJoinPool();
        TestTask testTask = new TestTask(0, 400, 100);
         pool.submit(testTask);
        System.out.println("submit...");
        System.out.println(pool.awaitTermination(1, TimeUnit.SECONDS));

        System.out.println("end..."+testTask.join());
    }
}
