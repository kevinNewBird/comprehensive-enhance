package com.base.util.time;
import java.util.Date;

/**
 * CalcFactory
 *
 * @description: CalcFactory
 * @author: deng.youxu
 * @since: 2020-01-16 11:21
 **/
public class CalcFactory {

	public static void main(String[] args) {
		Date date = new Date(120, 0, 11, 11, 34, 23);
		final long time = date.getTime();
		long dis = (System.currentTimeMillis() - time) / 1000;
		final com.base.util.time.SecondImpl second = new com.base.util.time.SecondImpl();
		final String desc = second.desc(dis);

		System.out.println(desc);

	}

}
