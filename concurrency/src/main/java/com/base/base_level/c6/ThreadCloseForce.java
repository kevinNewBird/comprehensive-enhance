package com.base.base_level.c6;

/**
 * @Author:zhao.song
 * @Date:2019/12/2 22:26
 * @Description:
 */
public class ThreadCloseForce {

    public static void main(String[] args) {
        ThreadService service = new ThreadService();

        long start = System.currentTimeMillis();
        service.execute(()->{
            //load a very heavy resource
            /*while(true){

            }*/
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown(1000);


        long end = System.currentTimeMillis();
        System.out.println(end-start);



    }
}
