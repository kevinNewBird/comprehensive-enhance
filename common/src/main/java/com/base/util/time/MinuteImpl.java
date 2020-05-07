package com.base.util.time;
/**
 * MinuteImpl
 *
 * @description: MinuteImpl
 * @author: deng.youxu
 * @since: 2020-01-16 00:20
 **/
public class MinuteImpl implements CalcDistance {


	@Override
	public String desc(long dis) {
		return isTrue(dis) ? getDistance(dis) + "分钟前" : new HourImpl().desc(dis);

	}

	@Override
	public boolean isTrue(long dis) {
		return dis > 60 && dis / 60 < 60;
	}

	@Override
	public int getDistance(long dis) {
		return (int) (dis / 60);
	}


}
