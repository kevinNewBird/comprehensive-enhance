package com.base.base_level.c3;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 16:07
 * @Description:
 */
public class CreateThread5 {
    private static int counter = 0;

    public static void main(String[] args) {
        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                counter++;

                new Thread(new Runnable() {
//                    byte[] data = new byte[1024 * 1204 * 2];
                    @Override
                    public void run() {
                        while(true){
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (Error error) {

        }

        System.out.println("Total created thread nums =>" + counter);
    }
}
