package com.base.util.time;
/**
 * DayImpl
 *
 * @description: DayImpl
 * @author: deng.youxu
 * @since: 2020-01-16 11:52
 **/
public class DayImpl implements CalcDistance {

	@Override
	public String desc(long dis) {
		return isTrue(dis) ? getDistance(dis) + "天前" : null;
	}

	@Override
	public boolean isTrue(long dis) {
		return getDistance(dis) > 1 && getDistance(dis) < 7;
	}

	@Override
	public int getDistance(long dis) {
		return (int) (dis / (60 * 60 * 24));
	}
}
