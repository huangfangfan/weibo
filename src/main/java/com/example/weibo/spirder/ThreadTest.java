package com.example.weibo.spirder;

import org.springframework.stereotype.Component;

/**
 * @program: weibo
 * @description:
 * @author: huang fangfan
 * @create: 2020-03-30 00:45
 */
@Component("threadTest")
public class ThreadTest implements Runnable{
    int pageNum = 1;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
       for (int i = 0;i<10;i++) {
               pageNum++;
               System.out.println(Thread.currentThread().getName() + "当前页数为：" + pageNum);
//               Thread.sleep(1000);
           }
       } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
