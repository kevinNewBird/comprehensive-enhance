package com.base.base_level.c3;

/**
 * @Author:zhao.song
 * @Date:2019/10/17 19:53
 * @Description:
 */
public class CreateThread3 {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        /*ThreadPoolExecutor executor1 = new ThreadPoolExecutor(5, 10, 200,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

        try {
            executor1.execute(new Runnable() {
                @Override
                public void run() {
                    ThreadPoolExecutor executor2 = new ThreadPoolExecutor(2, 5, 200,
                            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
                    executor2.execute(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("1111111");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            executor1.shutdown();

        }*/


        /*String str = "http://10.11.4.12/pub/zngqxz/tt/sp/201709/t20170915_1852617.html";
        System.out.println("str字符串占用字节数"+str.getBytes().length);*/


    }

}
