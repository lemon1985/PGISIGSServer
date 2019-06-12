package com.zondy.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import com.zondy.util.ByteUtil;

public class UDPClient {
	private static final int TIMEOUT = 5000;  //设置接收数据的超时时间
	private static final int MAXNUM = 5;      //设置重发数据的最多次数
	public static void main(String args[])throws IOException{
		//String str_send = "Hello UDPserver";
		//警力定位信息联网接入
		//byte[] str_send=sendData();
		//警力定位信息联网转发
		byte[] str_send=forwardData();
		
		byte[] buf = new byte[1024];
		//客户端在9000端口监听接收到的数据
		DatagramSocket ds = new DatagramSocket(9000);
		InetAddress loc = InetAddress.getLocalHost();
		//定义用来发送数据的DatagramPacket实例
		//DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),loc,3000);
		DatagramPacket dp_send= new DatagramPacket(str_send,str_send.length,loc,3000);
		//定义用来接收数据的DatagramPacket实例
		DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
		//数据发向本地3000端口
		ds.setSoTimeout(TIMEOUT);              //设置接收数据时阻塞的最长时间
		int tries = 0;                         //重发数据的次数
		boolean receivedResponse = false;     //是否接收到数据的标志位
		//直到接收到数据，或者重发次数达到预定值，则退出循环
		while(!receivedResponse && tries<MAXNUM){
			//发送数据
			ds.send(dp_send);
			try{
				//接收从服务端发送回来的数据
				ds.receive(dp_receive);
				//如果接收到的数据不是来自目标地址，则抛出异常
				if(!dp_receive.getAddress().equals(loc)){
					throw new IOException("Received packet from an unknown source");
				}
				//如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
				receivedResponse = true;
			}catch(InterruptedIOException e){
				//如果接收数据时阻塞超时，重发并减少一次重发的次数
				tries += 1;
				System.out.println("Time out," + (MAXNUM - tries) + " more tries..." );
			}
		}
		if(receivedResponse){
			//如果收到数据，则打印出来
			System.out.println("client received data from server：");
//			String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) + 
//					" from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
//			System.out.println(str_receive);
			//由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
			//所以这里要将dp_receive的内部消息长度重新置为1024
			dp_receive.setLength(1024);   
		}else{
			//如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
			System.out.println("No response -- give up.");
		}
		ds.close();
	}
	
	/**
	 * 警力定位信息联网接入
	 * @return
	 */
	public static byte[] sendData()
	{
		short header=(short) 0xAAAA;
		short orderFlag=(short)0xBBBB;
		short version=(short)0x2200;
		int bodyleng=47;
		String simid="zdbh1234567890000000";
		double x=119.12313;
		double y=34.232443;
		short speed=(short)100;
		short degree=(short)0x0000;
		short year=(short)2019;
		byte month=(byte)4; byte[] month_=new byte[1];month_[0]=month;
		byte date =(byte)15;byte[] date_=new byte[1];date_[0]=date;
		byte hour =(byte)17;byte[] hour_=new byte[1];hour_[0]=hour;
		byte minute=(byte)16;byte[] minute_=new byte[1];minute_[0]=minute;
		byte second=(byte)33;byte[] second_=new byte[1];second_[0]=second;
		return byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(
				byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(
				ByteUtil.getBytes(header)
				,ByteUtil.getBytes(orderFlag))
				,ByteUtil.getBytes(version))
				,ByteUtil.getBytes(bodyleng))
				,ByteUtil.getBytes(simid))
			    ,ByteUtil.getBytes(x))
			    ,ByteUtil.getBytes(y))
			    ,ByteUtil.getBytes(speed))
			    ,ByteUtil.getBytes(degree))
			    ,ByteUtil.getBytes(year))
			    ,month_)
				,date_)
			    ,hour_)
			    ,minute_)
			    ,second_);
	}
	
	/**
	 * 警力定位信息联网转发
	 * @return
	 */
	public static byte[] forwardData()
	{
		short header=(short) 0xAAAA;
		short orderFlag=(short)0xCCCC;
		short version=(short)0x2200;
		int bodyleng=51;
		String simid="zdbh1234567890000000";
		double x=119.12313;
		double y=34.232443;
		short speed=(short)100;
		short direction=(short)10;
		short hight=(short)20;
		short degree=(short)0x0000;
		short year=(short)2019;
		byte month=(byte)4; byte[] month_=new byte[1];month_[0]=month;
		byte date =(byte)15;byte[] date_=new byte[1];date_[0]=date;
		byte hour =(byte)17;byte[] hour_=new byte[1];hour_[0]=hour;
		byte minute=(byte)16;byte[] minute_=new byte[1];minute_[0]=minute;
		byte second=(byte)33;byte[] second_=new byte[1];second_[0]=second;
		return byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(
				byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(byteMerger(
				ByteUtil.getBytes(header)
				,ByteUtil.getBytes(orderFlag))
				,ByteUtil.getBytes(version))
				,ByteUtil.getBytes(bodyleng))
				,ByteUtil.getBytes(simid))
			    ,ByteUtil.getBytes(x))
			    ,ByteUtil.getBytes(y))
			    ,ByteUtil.getBytes(speed))
			    ,ByteUtil.getBytes(direction))
			    ,ByteUtil.getBytes(hight))
			    ,ByteUtil.getBytes(degree))
			    ,ByteUtil.getBytes(year))
			    ,month_)
				,date_)
			    ,hour_)
			    ,minute_)
			    ,second_);
	} 
	
	//java 合并两个byte数组 
	public static byte[] byteMerger(byte[] bt1, byte[] bt2){ 
	    byte[] bt3 = new byte[bt1.length+bt2.length]; 
		int i=0;
		    for(byte bt: bt1){
		     bt3[i]=bt;
		 i++;
		}
		     
		for(byte bt: bt2){
		  bt3[i]=bt;
		  i++;
		}
		System.out.println(bt3.length);
	    return bt3; 
	}
	
	

}
