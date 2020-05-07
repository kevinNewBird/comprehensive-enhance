package com.base.base_level.c6;

/**
 * @Author:zhao.song
 * @Date:2019/12/2 22:00
 * @Description:如何暴力的关闭线程:采用守护线程的原理
 */
public class ThreadService {
    private boolean finished = false;
    private Thread executeThread;

    public void execute(Runnable task) {
        executeThread = new Thread() {

            @Override
            public void run() {
                try {
                    Thread runner = new Thread(task);
                    runner.setDaemon(true);
                    runner.start();
                    runner.join();
                    finished = true;
                } catch (InterruptedException e) {

                }
            }
        };

        executeThread.start();
    }


    public void shutdown(long millis) {
        long currTime = System.currentTimeMillis();
        while (!finished) {
            if (System.currentTimeMillis() - currTime > millis) {
                System.out.println("执行超时了");
                executeThread.interrupt();
                break;
            }

            try {
                executeThread.sleep(1);

            } catch (InterruptedException e) {
                System.out.println("执行线程被打断");
                break;
            }
        }
        finished = false;
    }

}
