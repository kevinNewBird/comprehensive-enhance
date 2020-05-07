package com.base.base_level.c2;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 13:56
 * @Description:
 */
public class TaxCalaculatorMain {

    public static void main(String[] args) {
        /*TaxCalaculator calc = new TaxCalaculator(10000d,2000d){
            @Override
            protected Double calcTax() {
                return 0.9d;
            }
        };

        System.out.println(calc.calaculate());*/

        TaxCalaculator taxCalaculator = new TaxCalaculator(10000d, 2000d);
        taxCalaculator.setCalCulatorStrategy((salary,bonus)->{
            return salary*0.9+bonus*0.95;
        });

        System.out.println(taxCalaculator.calaculate());
    }
}
