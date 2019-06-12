package com.zondy.util;

public class MathUtil {
	
	/**
	 * 米转度
	 * @param meter
	 * @return
	 */
	public static double meter2degree(double meter)
	{
		return meter / (2 * Math.PI * 6371004) * 360;
	}
	
	public static double degree2meter(double degree)
	{
		return degree/360*2*(2 * Math.PI * 6371004);
	}
}
