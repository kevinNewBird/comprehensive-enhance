package com.base.base_level.c3;

/**
 * @Author:zhao.song
 * @Date:2019/10/18 15:50
 * @Description:
 */
public class CreateThread4 {

    private static int counter = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(null,new Runnable() {
            @Override
            public void run() {
                try {
                    add(0);
                } catch (Error e) {
                    System.out.println(counter);
                }
            }

            private void add(int i) {
                counter++;
                add(i + 1);
            }
        },"thread",1<<24);
        t1.start();
    }
}
