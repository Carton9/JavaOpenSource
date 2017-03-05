package adv.Web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class WebListener extends WebService{
	private static HashMap<String,WebListener> ListenerList=new HashMap<String,WebListener>();
	public WebListener(String name,String IP,Protocol type,int port) throws UndefineProtocolException, IOException{
		error=new UndefineProtocolException();
		init(type,IP,port);
	}

}
