package adv.Web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public abstract class WebServer extends Thread{
	WebListener listener;
	Thread listenerThread;
	ArrayList<WebClient> processerTCPlist=new ArrayList<WebClient>();
	ArrayList<Thread> processTCPThreadlist=new ArrayList<Thread>();
	Protocol type;String IP;int port;String name;
	boolean isAlive=false;
	WebServer self=this;
	class Listener extends WebListener{

		public Listener(String name, String IP, Protocol type, int port) throws UndefineProtocolException, IOException {
			super(name, IP, type, port);
			// TODO Auto-generated constructor stub
		}

		@Override
		public <T> T preProcess(T data) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void listenUndefine() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void initUndefine(String ip, int port) throws UndefineProtocolException, IOException {
			// TODO Auto-generated method stub
			throw new UndefineProtocolException();
		}

		@Override
		public void buildUndefine(Object Client) throws UndefineProtocolException, IOException {
			// TODO Auto-generated method stub
			throw new UndefineProtocolException();
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
		

	}
	class Tcp extends WebClient{

		public Tcp(Object Client) throws UndefineProtocolException, IOException {
			super(Client);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initUndefine(String ip, int port) throws UndefineProtocolException, IOException {
			// TODO Auto-generated method stub
			throw new UndefineProtocolException();
		}

		@Override
		public void buildUndefine(Object Client) throws UndefineProtocolException, IOException {
			// TODO Auto-generated method stub
			throw new UndefineProtocolException();
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
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				if(type==Protocol.UDP){
					try {
						sendUDP();
						reviceUDP();
						process(revice());
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
				else if(type==Protocol.TCP){
					try {
						sendTCP();
						reviceTCP();
						process(revice());
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
				else{
					try {
						sendUndefine();
						reviceUndefine();
						process(revice());
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
	public WebServer(String name,Protocol type,String IP,int port){
		this.type=type;
		this.IP=IP;
		this.port=port;
		this.name=name;
		isAlive=false;
	}
	@Override
	public void run() {
		try {
			listener=new Listener(name, IP, type, port);
		} catch (UndefineProtocolException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		listenerThread=new Thread(listener);
		listenerThread.start();
		isAlive=true;
		while(isAlive){
			if(type==Protocol.UDP){
				
			}else if(type==Protocol.TCP){
				Socket client=listener.getData();
				if(client==null)continue;
				WebClient clientProcess=null;
				try {
					clientProcess=makeTCPClient(client);
				} catch (UndefineProtocolException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				if(clientProcess!=null){
					processerTCPlist.add(clientProcess);
					Thread clientThread=new Thread(clientProcess);
					processTCPThreadlist.add(clientThread);
					clientThread.start();
				}
			}
		}
	}
	public WebClient makeTCPClient(Socket a) throws UndefineProtocolException, IOException{
		Tcp newClient=new Tcp(a);
		return newClient;
	}
	public abstract void process(byte[] data);
}