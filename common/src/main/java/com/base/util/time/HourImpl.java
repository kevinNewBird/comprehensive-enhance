package com.base.util.time;
/**
 * HourImpl
 *
 * @description: HourImpl
 * @author: deng.youxu
 * @since: 2020-01-16 11:34
 **/
public class HourImpl implements CalcDistance {
	@Override
	public String desc(long dis) {
		return isTrue(dis) ? getDistance(dis) + "小时前" : new DayImpl().desc(dis);
	}

	@Override
	public boolean isTrue(long dis) {
		return getDistance(dis) > 1 && getDistance(dis) < 24;
	}

	@Override
	public int getDistance(long dis) {
		return (int) (dis / (60 * 60));
	}
}
