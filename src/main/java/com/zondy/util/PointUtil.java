package com.zondy.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.zondy.bean.ENPoint;

public class PointUtil {
	
	/**
	    * @Title: randomENPoint
	    * @Description: 在矩形内随机生成点
	    * @param MinLon：最小经度  MaxLon： 最大经度   MinLat：最小纬度   MaxLat：最大纬度
	    * @return
	    * @throws
	    */
	public static ENPoint randomENPoint(double MinLon, double MaxLon, double MinLat, double MaxLat)
	{
	    BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
	    String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
	    db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
	    String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	    ENPoint point=new ENPoint();
	    String id=UUID.randomUUID().toString().replaceAll("-", "");
	    point.setId(id);
	    point.setX(Double.parseDouble(lon));
	    point.setY(Double.parseDouble(lat));
	    point.setSpeed(Math.random()*100);
	    point.setHight(Math.random()*10);
	    return point;
	}
	
	
	 /**
     * 函数功能：将原始经纬度坐标数据转换成度
     * @param str：原始经纬度坐标
     * @return ：返回对于的度数据
     */
    public static double dfTodu(String str){
        int indexD = str.indexOf('.');
        String strM = str.substring(0,indexD-2);
        String strN = str.substring(indexD-2);
        double d = Double.parseDouble(strM)+Double.parseDouble(strN)/60;
        return d;
    }
    /**
     * 函数功能：保留一个double数的小数点后六位
     * @param d：原始double数
     * @return 返回转换后的double数
     */
    public static double getPointSix(double d){
        DecimalFormat df = new DecimalFormat("0.000000");
        return Double.parseDouble(df.format(d));
    }
    /**
     * 函数功能：使用三角形面积（使用海伦公式求得）相等方法计算点pX到点pA和pB所确定的直线的距离
     * @param pA：起始点
     * @param pB：结束点
     * @param pX：第三个点
     * @return distance：点pX到pA和pB所在直线的距离
     */
    public static double distToSegment(ENPoint pA,ENPoint pB,ENPoint pX){
        double a = Math.abs(geoDist(pA, pB));
        double b = Math.abs(geoDist(pA, pX));
        double c = Math.abs(geoDist(pB, pX));
        double p = (a+b+c)/2.0;
        double s = Math.sqrt(Math.abs(p*(p-a)*(p-b)*(p-c)));
        double d = s*2.0/a;
        return d;
    }

    /**
     * 函数功能：求两个经纬度点之间的距离
     * @param pA：起始点
     * @param pB：结束点
     * @return distance：距离
     */
//    public static double geoDist(ENPoint pA,ENPoint pB)
//    {
//        double radLat1 = Rad(pA.y);
//        double radLat2 = Rad(pB.y);
//        double delta_lon = Rad(pB.x - pA.x);
//        double top_1 = Math.cos(radLat2) * Math.sin(delta_lon);
//        double top_2 = Math.cos(radLat1) * Math.sin(radLat2) - Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
//        double top = Math.sqrt(top_1 * top_1 + top_2 * top_2);
//        double bottom = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
//        double delta_sigma = Math.atan2(top, bottom);
//        double distance = delta_sigma * 6378137.0;
//        return distance;
//    }
    
    private static final double EARTH_RADIUS = 6371393;
    /**
     * 通过AB点经纬度获取距离
     * @param pointA A点(经，纬)
     * @param pointB B点(经，纬)
     * @return 距离(单位：米)
     */
    public static double geoDist(ENPoint pointA, ENPoint pointB) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(pointA.getX()); // A经弧度
        double radiansAY = Math.toRadians(pointA.getY()); // A纬弧度
        double radiansBX = Math.toRadians(pointB.getX()); // B经弧度
        double radiansBY = Math.toRadians(pointB.getY()); // B纬弧度
 
        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }
    
    /**
     * 函数功能：角度转弧度
     * @param d：角度
     * @return 返回的是弧度
     */
    public static double Rad(double d)
    {
        return d * Math.PI / 180.0;
    }
    
    
    public static ArrayList<ENPoint> DouglasPeucker(List<ENPoint> points, double epsilon) {
        // 找到最大阈值点，即操作（1）
        double maxH = 0;
        int index = 0;
        int end = points.size();
        for (int i = 1; i < end - 1; i++) {
            double h = distToSegment(points.get(i), points.get(0), points.get(end - 1));
            if (h > maxH) {
                maxH = h;
                index = i;
            }
        }

        // 如果存在最大阈值点，就进行递归遍历出所有最大阈值点
        ArrayList<ENPoint> result = new ArrayList<>();
        if (maxH > epsilon) {
        	ArrayList<ENPoint> leftPoints = new ArrayList<>();// 左曲线
        	ArrayList<ENPoint> rightPoints = new ArrayList<>();// 右曲线
            // 分别提取出左曲线和右曲线的坐标点
            for (int i = 0; i < end; i++) {
                if (i <= index) {
                    leftPoints.add(points.get(i));
                    if (i == index)
                        rightPoints.add(points.get(i));
                } else {
                    rightPoints.add(points.get(i));
                }
            }

            // 分别保存两边遍历的结果
            ArrayList<ENPoint> leftResult = new ArrayList<>();
            ArrayList<ENPoint> rightResult = new ArrayList<>();
            leftResult = DouglasPeucker(leftPoints, epsilon);
            rightResult = DouglasPeucker(rightPoints, epsilon);

            // 将两边的结果整合
            rightResult.remove(0);//移除重复点
            leftResult.addAll(rightResult);
            result = leftResult;
        } else {// 如果不存在最大阈值点则返回当前遍历的子曲线的起始点
            result.add(points.get(0));
            result.add(points.get(end - 1));
        }
        return result;
    }
    
    /**
     * 函数功能：根据最大距离限制，采用DP方法递归的对原始轨迹进行采样，得到压缩后的轨迹
     * @param enpInit：原始经纬度坐标点数组
     * @param enpArrayFilter：保持过滤后的点坐标数组
     * @param start：起始下标
     * @param end：终点下标
     * @param DMax：预先指定好的最大距离误差
     */
    public static ArrayList<ENPoint> TrajCompressC(ENPoint[] enpInit,ArrayList<ENPoint> enpArrayFilter,
                                     int start,int end,double DMax){
        if(start < end){//递归进行的条件
            double maxDist = 0;//最大距离
            int cur_pt = 0;//当前下标
            for(int i=start+1;i<end;i++){
                double curDist = distToSegment(enpInit[start],enpInit[end],enpInit[i]);//当前点到对应线段的距离
                if(curDist > maxDist){
                    maxDist = curDist;
                    cur_pt = i;
                }//求出最大距离及最大距离对应点的下标
            }
            //若当前最大距离大于最大距离误差
            if(maxDist >= DMax){
                enpArrayFilter.add(enpInit[cur_pt]);//将当前点加入到过滤数组中
                //将原来的线段以当前点为中心拆成两段，分别进行递归处理
                TrajCompressC(enpInit,enpArrayFilter,start,cur_pt,DMax);
                TrajCompressC(enpInit,enpArrayFilter,cur_pt,end,DMax);
            }
        }
        return enpArrayFilter;
    }
    /**
     * 函数功能：求平均距离误差
     * @param pGPSArrayInit：原始数据点坐标
     * @param pGPSArrayFilterSort：过滤后的数据点坐标
     * @return ：返回平均距离
     */
    public static double getMeanDistError(
            ArrayList<ENPoint> pGPSArrayInit,ArrayList<ENPoint> pGPSArrayFilterSort){
        double sumDist = 0.0;
        for(int i=1;i<pGPSArrayFilterSort.size();i++){
            int start = pGPSArrayFilterSort.get(i-1).xh;
            int end = pGPSArrayFilterSort.get(i).xh;
            for(int j=start+1;j<end;j++){
                sumDist += distToSegment(
                        pGPSArrayInit.get(start),pGPSArrayInit.get(end),pGPSArrayInit.get(j));
            }
        }
        double meanDist = sumDist/(pGPSArrayInit.size());
        return meanDist;
    }
}
