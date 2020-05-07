package com.base.util.time;
/**
 * SecondImpl
 *
 * @description: SecondImpl
 * @author: deng.youxu
 * @since: 2020-01-16 00:14
 **/
public class SecondImpl implements CalcDistance {

	@Override
	public String desc(long dis) {
		return isTrue(dis) ? getDistance(dis) + "秒前" : new com.base.util.time.MinuteImpl().desc(dis);
	}

	/**
	 * 单位秒 dis
	 * @param dis
	 * @return
	 */
	@Override
	public boolean isTrue(long dis) {
		return dis < 60 ? true : false;
	}

	@Override
	public int getDistance(long dis) {
		return (int) dis;
	}


}
