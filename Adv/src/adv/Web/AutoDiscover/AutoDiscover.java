package adv.Web.AutoDiscover;

import java.net.InetAddress;
import java.net.UnknownHostException;

import adv.Web.*;

public abstract class AutoDiscover {
	ServiceType type;
	protected InetAddress ip;
	int port;
	Protocol protocol;
	WebClient connection;
	WebServer server;
	AutoDiscoverListener listener;
	public AutoDiscover(ServiceType type,String thisIP,int listenerPort) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		ip=InetAddress.getByName(thisIP);
		this.port=listenerPort;
		this.type=type;
		this.protocol=protocol;
	}
	public abstract void Listening();
	public abstract void search();
	public abstract void disconnect();
}
