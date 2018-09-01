package adv.Web.AutoDiscover;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import adv.Web.*;

public class AutoDiscover extends Thread{
	ServiceType type;
	protected InetAddress ip;
	int port;
	Protocol protocol;
	WebClient connection;
	WebServer server;
	AutoDiscoverListener listener;
	Thread listenerThread;
	String bordcastMessege;
	final String checkCode="r1Eva$Jt";
	public AutoDiscover(ServiceType type,String thisIP,int listenerPort,String name) throws UndefineProtocolException, IOException {
		// TODO Auto-generated constructor stub
		ip=InetAddress.getByName(thisIP);
		this.port=listenerPort;
		this.type=type;
		this.protocol=protocol;
		if(type.getValue()==0){
			listener=new AutoDiscoverListener(name, thisIP, listenerPort, thisIP, listenerPort);
			listenerThread=new Thread(listener);
			listenerThread.start();
		}
	}
	@Override
	public void run() {
		if(listener.isConnect()){
			
		}
	}
}
