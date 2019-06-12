package com.zondy.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

import com.zondy.util.ByteUtil;

/**
 * 服务器端，实现基于UDP的用户登录
 * Created by lemon on 2019/04/15.
 */
public class UDPServer {
	public static void main(String[] args)  throws  SocketException , IOException{
		String str_send = "Hello UDPclient";
		byte[] buf = new byte[1024];
		//服务端在3000端口监听接收到的数据
		DatagramSocket ds = new DatagramSocket(3000);
		//接收从客户端发送过来的数据
		DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
		System.out.println("server is on，waiting for client to send data......");
		boolean f = true;
		while(f){
			//服务器端接收来自客户端的数据
			ds.receive(dp_receive);
			System.out.println("server received data from client：");
//			String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) + 
//					" from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
//			System.out.println(str_receive);
			
			System.out.println("Msg from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort());
			
			byte[] orderFlagBuf=Arrays.copyOfRange(dp_receive.getData(), 2, 4);
			short orderFlag=ByteUtil.getShort(orderFlagBuf);
			getorderFlag(orderFlag);
			
			//recvData(dp_receive); //警力定位信息联网接入
			recvData2(dp_receive);//警力定位信息联网转发
			//数据发动到客户端的3000端口
			DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),dp_receive.getAddress(),9000);
			ds.send(dp_send);
			//由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
			//所以这里要将dp_receive的内部消息长度重新置为1024
			dp_receive.setLength(1024);
		}
		ds.close();
    }
	
	public static void getorderFlag(short order)
	{
		if(order==0xBBBB)
		{
			System.out.println("登录系统");
		}else if(order==0xBBCC)
		{
			System.out.println("登录应答");
		}else if(order==0xCCCC)
		{
			System.out.println("定位数据");
		}else if(order==0xEEEE)
		{
			System.out.println("心跳检测");
		}else if(order==0xFFFF)
		{
			System.out.println("断开连接");
		}else if(order==0xDDDD)
		{
			System.out.println("报警或自定义");
		}
	}
	
	public static void recvData(DatagramPacket dp)
	{
		byte[] recvbuf=dp.getData();
		int leng=dp.getLength();
		byte[] headerBuf=Arrays.copyOfRange(recvbuf, 0, 2);
		short header=ByteUtil.getShort(headerBuf);
		System.out.println("标志头:"+header);
		byte[] orderFlagBuf=Arrays.copyOfRange(recvbuf, 2, 4);
		short orderFlag=ByteUtil.getShort(orderFlagBuf);
		System.out.println("命令字:"+orderFlag);
		byte[] versionBuf=Arrays.copyOfRange(recvbuf, 4, 6);
		short version=ByteUtil.getShort(versionBuf);
		System.out.println("版本号:"+version);
		byte[] bodylengBuf=Arrays.copyOfRange(recvbuf, 6, 10);
		int bodyleng=ByteUtil.getInt(bodylengBuf);
		System.out.println("包体大小:"+bodyleng);
		byte[] bodyBuf=Arrays.copyOfRange(recvbuf, 10, leng);
		
		byte[] idBuf;
		if(bodyBuf[19]==0){
			idBuf=Arrays.copyOfRange(bodyBuf, 0, 19);
		}else{
			idBuf=Arrays.copyOfRange(bodyBuf, 0, 20);
		}
		final String simid=new String(idBuf);
		System.out.print("终端编号:"+simid);
		byte[] xBuf=Arrays.copyOfRange(bodyBuf, 20, 28);
		final double x=ByteUtil.getDouble(xBuf);
		System.out.print("经度:"+x);
		byte[] yBuf=Arrays.copyOfRange(bodyBuf, 28, 36);
		final double y=ByteUtil.getDouble(yBuf);
		System.out.print("纬度:"+y);
		byte[] speedBuf=Arrays.copyOfRange(bodyBuf, 36, 38);
		final short speed=ByteUtil.getShort(speedBuf);
		System.out.print("速度:"+speed);
		byte[] degreeBuf=Arrays.copyOfRange(bodyBuf, 38, 40);
		short degree=ByteUtil.getShort(degreeBuf);
		System.out.print("精度:"+degree);
		byte[] yearBuf=Arrays.copyOfRange(bodyBuf, 40, 42);
		short year=ByteUtil.getShort(yearBuf);
		System.out.print(","+year+"年");
		byte month=bodyBuf[42];
		System.out.print(month+"月");
		byte date=bodyBuf[43];
		System.out.print(date+"日:");
		byte hour=bodyBuf[44];
		System.out.print(" "+hour+"时");
		byte minute=bodyBuf[45];
		System.out.print(minute+"分");
		byte second=bodyBuf[46];
		System.out.println(second+"秒");
	}
	
	public static void recvData2(DatagramPacket dp)
	{
		byte[] recvbuf=dp.getData();
		int leng=dp.getLength();
		byte[] headerBuf=Arrays.copyOfRange(recvbuf, 0, 2);
		short header=ByteUtil.getShort(headerBuf);
		System.out.println("标志头:"+header);
		byte[] orderFlagBuf=Arrays.copyOfRange(recvbuf, 2, 4);
		short orderFlag=ByteUtil.getShort(orderFlagBuf);
		System.out.println("命令字:"+orderFlag);
		byte[] versionBuf=Arrays.copyOfRange(recvbuf, 4, 6);
		short version=ByteUtil.getShort(versionBuf);
		System.out.println("版本号:"+version);
		byte[] bodylengBuf=Arrays.copyOfRange(recvbuf, 6, 10);
		int bodyleng=ByteUtil.getInt(bodylengBuf);
		System.out.println("包体大小:"+bodyleng);
		byte[] bodyBuf=Arrays.copyOfRange(recvbuf, 10, leng);
		
		byte[] idBuf;
		if(bodyBuf[19]==0){
			idBuf=Arrays.copyOfRange(bodyBuf, 0, 19);
		}else{
			idBuf=Arrays.copyOfRange(bodyBuf, 0, 20);
		}
		final String simid=new String(idBuf);
		System.out.print("终端编号:"+simid);
		byte[] xBuf=Arrays.copyOfRange(bodyBuf, 20, 28);
		final double x=ByteUtil.getDouble(xBuf);
		System.out.print("经度:"+x);
		byte[] yBuf=Arrays.copyOfRange(bodyBuf, 28, 36);
		final double y=ByteUtil.getDouble(yBuf);
		System.out.print("纬度:"+y);
		byte[] speedBuf=Arrays.copyOfRange(bodyBuf, 36, 38);
		final short speed=ByteUtil.getShort(speedBuf);
		System.out.print("速度:"+speed);
		byte[] directionBuf=Arrays.copyOfRange(bodyBuf, 38, 40);
		final short direction=ByteUtil.getShort(directionBuf);
		System.out.print("方向:"+direction);
		byte[] hightBuf=Arrays.copyOfRange(bodyBuf, 40, 42);
		final short hight=ByteUtil.getShort(hightBuf);
		System.out.print("高程:"+hight);
		byte[] degreeBuf=Arrays.copyOfRange(bodyBuf, 42, 44);
		short degree=ByteUtil.getShort(degreeBuf);
		System.out.print("精度:"+degree);
		byte[] yearBuf=Arrays.copyOfRange(bodyBuf, 44, 46);
		short year=ByteUtil.getShort(yearBuf);
		System.out.print(","+year+"年");
		byte month=bodyBuf[46];
		System.out.print(month+"月");
		byte date=bodyBuf[47];
		System.out.print(date+"日:");
		byte hour=bodyBuf[48];
		System.out.print(" "+hour+"时");
		byte minute=bodyBuf[49];
		System.out.print(minute+"分");
		byte second=bodyBuf[50];
		System.out.println(second+"秒");
	}
	
}
