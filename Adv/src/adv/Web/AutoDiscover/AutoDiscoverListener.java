package adv.Web.AutoDiscover;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import adv.Web.*;
/** 
 * This class provide listening service for auto discover.
 * @author Mike Cai
 * @version v1.00
 */
public class AutoDiscoverListener extends WebService {
	final String checkCode="r1Eva$Jt";
	ArrayList<UserInfo> userList=new ArrayList<UserInfo>();
	String serverIP;
	int serverPort;
	String bordcastMessege;
	String serverName;

	/**
	 * Constractor.
	 * @param  pointer-which data in data list user want to get.
	 */
	public AutoDiscoverListener(String serverName,String listenerIP,int listenerPort,String serverIP,int serverPort) throws UndefineProtocolException, IOException {
		error=new UndefineProtocolException();
		this.serverIP=serverIP;
		this.serverPort=serverPort;
		bordcastMessege="#"+checkCode+"#"+serverName+"#"+serverIP+"#"+serverPort+"#";
		init(Protocol.UDP,"255.255.255.255",listenerPort);
	}
	@Override
	protected void sendUDP() throws IOException{
		byte[] data=bordcastMessege.getBytes();
		outputUDP= new DatagramPacket(data,data.length,ip,port); 
		ClientUDP.send(outputUDP);
	}
	@Override
	protected void reviceUDP() throws IOException,SocketTimeoutException {
		byte[] buff=new byte[4096];
		inputUDP=new DatagramPacket(buff,buff.length);
		ClientUDP.receive(inputUDP);  
		String rawInfo=new String(inputUDP.getData());
		String rawInfos[]=rawInfo.split("#");
		if(rawInfos[0].equals(checkCode)){
			UserInfo info=new UserInfo();
			info.setName(rawInfos[1]);
			info.setIP(rawInfos[2]);
			info.setPort(Integer.parseInt(rawInfos[3]));
			userList.add(info);
		}
		
		//outputStock.add(inputUDP.getData());
		DatagramPacket outputUDP= new DatagramPacket("OK".getBytes(),"OK".length(),ip,port); 
		ClientUDP.send(outputUDP);
	}
	/**
	 * This method is disable
	 * @param  null
	 * @return null
	 */
	@Override
	public void send(byte[] data){
		/*if(data==null)return;
		inputStock.add(data);
		if(type==Protocol.UDP){
			try {
				sendUDP();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Timer sender=new Timer(true);
		TimerTask task=new TimerTask(){

			@Override
			public void run() {
				try {
					sendUDP();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		};
		sender.schedule(task, 0, 10);
		isAlive=true;
		while(isAlive){
			try {
				reviceUDP();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sender.cancel();
	}
	/**
	 * This method is used to close listener
	 * @param  null
	 * @return null
	 */
	public void kill(){
		this.isAlive=false;
	}
	
	@Override
	public void initUndefine(String ip, int port) throws UndefineProtocolException, IOException {
		// TODO Auto-generated method stub
		throw error;
	}

	@Override
	public void buildUndefine(Object Client) throws UndefineProtocolException, IOException {
		// TODO Auto-generated method stub
		throw error;
	}

	@Override
	public void sendUndefine() throws IOException {
		// TODO Auto-generated method stub
		throw new IOException();
	}

	@Override
	public void reviceUndefine() throws IOException {
		// TODO Auto-generated method stub
		throw new IOException();
	}

	@Override
	public void closeUndefine() throws IOException {
		// TODO Auto-generated method stub
		throw new IOException();
	}
	
	public UserInfo getUser(){
		UserInfo user=userList.get(0);
		userList.remove(0);
		return user;
	}
}
