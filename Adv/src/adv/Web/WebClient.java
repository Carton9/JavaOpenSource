package adv.Web;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
//fix
public abstract class WebClient extends WebService{
	//T Client;ed
	private static ArrayList<WebClient> ClientList=new ArrayList<WebClient>();
	public WebClient(Protocol type,String IP,int port) throws UndefineProtocolException, IOException{
		error=new UndefineProtocolException();
		init(type,IP,port);
	}
	@SuppressWarnings("unchecked")
	public WebClient(Object Client) throws UndefineProtocolException, IOException {
		
		error=new UndefineProtocolException();
		if(Client==null)throw new IOException();
		// TODO Auto-generated constructor stub
		if(Client.getClass().getName().equals("java.net.DatagramSocket")){
			type=Protocol.UDP;
			ClientUDP=(DatagramSocket)Client;
			build(ClientUDP);
			isAlive=true;
			ClientList.add(this);
		}
		else if(Client.getClass().getName().equals("java.net.Socket")){
			type=Protocol.TCP;
			ClientTCP=(Socket)Client;
			build(ClientTCP);
			isAlive=true;
			ClientList.add(this);
		}
		else throw error;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(type==Protocol.UDP){
				try {
					sendUDP();
					reviceUDP();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
				}
			}
			else if(type==Protocol.TCP){
				try {
					sendTCP();
					reviceTCP();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
				}
			}
			else{
				try {
					sendUndefine();
					reviceUndefine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

}
