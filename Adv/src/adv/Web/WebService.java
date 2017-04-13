package adv.Web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
/** 
 * This class provide methods to connect other computer.
 * @author Mike Cai
 * @version v1.00
 */
public abstract class WebService implements Runnable ,AutoCloseable{
	protected volatile boolean isAlive=false;
	protected InetAddress ip;
	protected String ipString;
	protected int port;
	protected Protocol type;
	protected UndefineProtocolException error=new UndefineProtocolException();;
	protected volatile ArrayList<byte[]> inputStock=new ArrayList<byte[]>();
	protected volatile ArrayList<byte[]> outputStock=new ArrayList<byte[]>();
	protected DatagramSocket ClientUDP=null;
	protected DatagramPacket outputUDP=null;
	protected DatagramPacket inputUDP=null;
	
	protected Socket ClientTCP=null;
	protected OutputStream outputTCP=null;
	protected InputStream inputTCP=null;
	/**
	 * This method use to initialize service.
	 * @return null.
	 */
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
			//ClientList.add(this);
		}
		else if(type==Protocol.TCP){
			
			//ClientTCP=(Socket)Client;
			ClientTCP=new Socket(ip, port);
			// TODO Auto-generated constructor stub
			build(ClientTCP);
			isAlive=true;
			//ClientList.add(this);
		}
		else initUndefine(IP,port);
	}
	protected void build(Object Client) throws UndefineProtocolException, IOException{
		// TODO Auto-generated constructor stub
		if(Client==null)throw new IOException();
		if(Client.getClass().getName().equals("java.net.DatagramSocket")){
			DatagramSocket ClientUDP=(DatagramSocket)Client;
			ClientUDP.setSoTimeout(50);
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
	/**
	 * This method use to close service.
	 * @return null.
	 */
	@Override
	public void close()throws IOException{
		if(type==Protocol.TCP){
			ClientTCP.close();
			isAlive=false;
			//ClientList.remove(this);
		}
		else if(type==Protocol.UDP){
			ClientUDP.close();
			isAlive=false;
			//ClientList.remove(this);
		}
		else closeUndefine();
		isAlive=false;
	}
	protected void sendTCP() throws IOException{
		if(inputStock.isEmpty())return;
		byte[] data=inputStock.get(0);
		inputStock.remove(0);
		outputTCP.write(data, 0, data.length);
	}
	protected void sendUDP() throws IOException{
		if(inputStock.isEmpty())return;
		byte[] data=inputStock.get(0);
		byte[] buff=new byte[4096];
		inputStock.remove(0);
		inputUDP=new DatagramPacket(buff,buff.length);
		outputUDP= new DatagramPacket(data,data.length,ip,port); 
		ClientUDP.send(outputUDP);
	}
	protected void reviceTCP() throws IOException{
		byte[] buff=new byte[4096];
		inputTCP.read(buff, 0, 4096);
		outputStock.add(buff);
	}
	protected void reviceUDP() throws IOException,SocketTimeoutException {
		byte[] buff=new byte[4096];
		inputUDP=new DatagramPacket(buff,buff.length);
		ClientUDP.receive(inputUDP);  
		outputStock.add(inputUDP.getData());
		DatagramPacket outputUDP= new DatagramPacket("OK".getBytes(),"OK".length(),ip,port); 
		ClientUDP.send(outputUDP);
	}
	/**
	 * This method use to add data to sending list.
	 * @param  data.
	 * @return null.
	 */
	public void send(byte[] data){
		if(data==null)return;
		inputStock.add(data);
	}
	/**
	 * This method use to get data to revicing list.
	 * @param  null.
	 * @return data.
	 */
	public byte[] revice(){
		if(outputStock.isEmpty())return null;
		byte[] buff=outputStock.get(0);
		outputStock.remove(0);
		return buff;
	}
	/**
	 * This method use to get data to revicing list.
	 * @return is alive.
	 */
	public boolean isConnect(){
		return isAlive;
	}
	/**
	 * This method use to initialize undefine service.
	 * @param ip, port.
	 * @return null.
	 */
	public abstract void initUndefine(String ip,int port) throws UndefineProtocolException, IOException;
	/**
	 * This method use to set up input and output for undefine service.
	 * @param Client.
	 * @return null.
	 */
	public abstract void buildUndefine(Object Client) throws UndefineProtocolException, IOException;
	/**
	 * This method use to send data in the sending list by undefine service.
	 * @param null.
	 * @return null.
	 */
	public abstract void sendUndefine()throws IOException;
	/**
	 * This method use to revice data to the revicing list by undefine service.
	 * @param null.
	 * @return null.
	 */
	public abstract void reviceUndefine() throws IOException;
	/**
	 * This method use to close undefine service.
	 * @param null.
	 * @return null.
	 */
	public abstract void closeUndefine() throws IOException;
}
