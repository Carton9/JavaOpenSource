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

public abstract class WebClient implements WebInterface, Runnable ,AutoCloseable{
	//T Client;
	private static ArrayList<WebClient> ClientList=new ArrayList<WebClient>();
	boolean isAlive=false;
	InetAddress ip;
	String ipString;
	int port;
	Protocol type;
	final UndefineProtocolException error;
	ArrayList<byte[]> inputStock=new ArrayList<byte[]>();
	ArrayList<byte[]> outputStock=new ArrayList<byte[]>();
	private DatagramSocket ClientUDP=null;
	private DatagramPacket outputUDP=null;
	private DatagramPacket inputUDP=null;
	
	private Socket ClientTCP=null;
	private OutputStream outputTCP=null;
	private InputStream inputTCP=null;
	public WebClient(){
		error=new UndefineProtocolException();
	}
	@SuppressWarnings("unchecked")
	public WebClient(Object Client) throws UndefineProtocolException, IOException {
		
		error=new UndefineProtocolException();
		if(Client==null)throw new IOException();
		// TODO Auto-generated constructor stub
		if(Client.getClass().getName().equals("java.net.DatagramSocket")){
			ClientUDP=(DatagramSocket)Client;
			build(ClientUDP);
			isAlive=true;
			ClientList.add(this);
		}
		else if(Client.getClass().getName().equals("java.net.Socket")){
			ClientTCP=(Socket)Client;
			build(ClientTCP);
			isAlive=true;
			ClientList.add(this);
		}
		else throw error;
	}
	public void init(Protocol type,String IP,int port) throws UndefineProtocolException, IOException {
		//error=new UndefineProtocolException();
		// TODO Auto-generated constructor stub
		ip=InetAddress.getByName(IP);
		this.type=type;
		if(type==Protocol.UDP){
			//ClientUDP=(DatagramSocket)Client;
			this.port=port;
			ClientUDP=new DatagramSocket(port);
			build(ClientUDP);
			isAlive=true;
			ClientList.add(this);
		}
		else if(type==Protocol.TCP){
			
			//ClientTCP=(Socket)Client;
			ClientTCP=new Socket(ip, port);
			// TODO Auto-generated constructor stub
			build(ClientTCP);
			isAlive=true;
			ClientList.add(this);
		}
		else initUndefine(IP,port);
	}
	private void build(Object Client) throws UndefineProtocolException, IOException{
		// TODO Auto-generated constructor stub
		if(Client==null)throw new IOException();
		if(Client.getClass().getName().equals("java.net.DatagramSocket")){
			DatagramSocket ClientUDP=(DatagramSocket)Client;
			ClientUDP.setSoTimeout(5000);
		}
		else if(Client.getClass().getName().equals("java.net.Socket")){
			Socket ClientTCP=(Socket)Client;
			outputTCP=ClientTCP.getOutputStream();
			inputTCP=ClientTCP.getInputStream();
		}
		else{
			//throw error;
			buildUndefine(Client);
		}
	}
	
	//public void reconnect(String ip,int port){
	//	if(ClientTCP!=null&&!ClientTCP.isClosed()){
			//ClientTCP.
	//	}
	//}
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
	@Override
	public void close()throws IOException{
		if(ClientTCP!=null&&!ClientTCP.isClosed()){
			ClientTCP.close();
			isAlive=false;
			ClientList.remove(this);
		}
		else if(ClientUDP!=null&&!ClientUDP.isClosed()){
			ClientUDP.close();
			isAlive=false;
			ClientList.remove(this);
		}
		else closeUndefine();
	}
	private void sendTCP() throws IOException{
		byte[] data=inputStock.get(0);
		inputStock.remove(0);
		outputTCP.write(data, 0, data.length);
	}
	
	private void sendUDP() throws IOException{
		byte[] data=inputStock.get(0);
		byte[] buff=new byte[4096];
		inputStock.remove(0);
		inputUDP=new DatagramPacket(buff,buff.length);
		outputUDP= new DatagramPacket(data,data.length,ip,port); 
		for(int i=0;i<5;i++){
			ClientUDP.send(outputUDP);
			ClientUDP.receive(inputUDP);
			if(inputUDP.getAddress().equals(ip)){
				String respone=new String(inputUDP.getData());
				if(respone.equals("OK"))break;
				else outputStock.add(inputUDP.getData());
			}
		}
	}
	
	private void reviceTCP() throws IOException{
		byte[] buff=new byte[4096];
		inputTCP.read(buff, 0, 4096);
		outputStock.add(buff);
	}
	private void reviceUDP() throws IOException{
		byte[] buff=new byte[4096];
		inputUDP=new DatagramPacket(buff,buff.length);
		ClientUDP.receive(inputUDP);  
		outputStock.add(inputUDP.getData());
		DatagramPacket outputUDP= new DatagramPacket("OK".getBytes(),"OK".length(),ip,port); 
		ClientUDP.send(outputUDP);
	}
	public void send(byte[] data){
		if(data==null)return;
		inputStock.add(data);
	}
	public byte[] revice(){
		if(outputStock.isEmpty())return null;
		byte[] buff=outputStock.get(0);
		outputStock.remove(0);
		return buff;
	}
	public boolean isConnect(){
		return isAlive;
	}
	public abstract void initUndefine(String ip,int port) throws UndefineProtocolException, IOException;
	public abstract void buildUndefine(Object Client) throws UndefineProtocolException, IOException;
	public abstract void sendUndefine()throws IOException;
	public abstract void reviceUndefine() throws IOException;
	public abstract void closeUndefine() throws IOException;
}
