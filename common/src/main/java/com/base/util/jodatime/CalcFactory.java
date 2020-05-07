package com.base.util.jodatime;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;




/**
 * CalcFactory
 *
 * @description: CalcFactory
 * @author: deng.youxu
 * @since: 2020-01-16 11:21
 **/
public class CalcFactory {

	private static final Map<Integer, String> INDEX_DESC_MAP = new HashMap<Integer, String>() {
		{
			put(0, "年");
			put(1, "月");
			put(2, "周");
			put(3, "天");
			put(4, "时");
			put(5, "分");
			put(6, "秒");
			put(7, "毫秒");
		}
	};

	public static void main(String[] args) {
		DateTime start = new DateTime(2020,1,15,12,20);
		Period p = new Period(start, DateTime.now());
		final String dis = find(p).orElse("无法获取");
		System.out.println(dis);
	}

	private static Optional<String> find(Period p) {

		final Optional<String> s = INDEX_DESC_MAP.keySet().stream().filter((Integer key) -> p.getValue(key) != 0)
				.findFirst().map((Integer key) ->
						p.getValue(key) + INDEX_DESC_MAP.get(key));
		return s;
	}
}
