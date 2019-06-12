package com.zondy.bean;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ENPoint implements Comparable<ENPoint>{
	 public String id;//点ID
	 public double x;//经度
	 public double y;//维度
	 public double speed;//速度
	 public double hight;//高程
	 public String time;//时间
	 public int xh;

	public ENPoint(){}//空构造函数
    public String toString(){
        //DecimalFormat df = new DecimalFormat("0.000000");
        return this.x+","+this.y+","+this.speed+","+this.hight+","+this.time;
    }
    public String getTestString(){
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(this.x)+","+df.format(this.y);
    }
    public String getResultString(){
        DecimalFormat df = new DecimalFormat("0.000000");
        return this.id+"#"+df.format(this.x)+","+df.format(this.y);
    }
    @Override
    public int compareTo(ENPoint other) {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			if(dateFormat.parse(this.time).getTime() < dateFormat.parse(other.getTime()).getTime())  return -1;
			else if(dateFormat.parse(this.time).getTime() > dateFormat.parse(other.getTime()).getTime())  return 1;
			else
			    return 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getHight() {
		return hight;
	}
	public void setHight(double hight) {
		this.hight = hight;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
    
}
