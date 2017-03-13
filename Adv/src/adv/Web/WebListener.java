package adv.Web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//fix
public abstract class WebListener extends WebService{
	private static HashMap<String,WebListener> ListenerList=new HashMap<String,WebListener>();
	private volatile ArrayList processedDataList=new ArrayList();
	private ServerSocket listener;
	volatile ArrayList<DatagramPacket> outputUDPStock =new ArrayList<DatagramPacket>();
	volatile ArrayList<DatagramPacket> inputUDPStock =new ArrayList<DatagramPacket>();
	public WebListener(String name,String IP,Protocol type,int port) throws UndefineProtocolException, IOException{
		error=new UndefineProtocolException();
		init(type,IP,port);
		ListenerList.put(name, this);
	}
	@Override
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
			listener = new ServerSocket(port);
			// TODO Auto-generated constructor stub
			//build(ClientTCP);
			isAlive=true;
			//ClientList.add(this);
		}
		else initUndefine(IP,port);
	}
	@Override
	public void run() {
		while(true){
			if(type==Protocol.UDP){
				try {
					listenUDP();
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
			}else if(type==Protocol.TCP){
					try {
						listenTCP();
					} catch (UndefineProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
			}else{
				listenUndefine();
			}
		}
	}
	private void listenUDP() throws IOException{
		reviceUDP();
		processedDataList.add(preProcess(reviceUDPPackage()));
	}
	private void listenTCP() throws UndefineProtocolException{
		Socket client=null;
		try {
			client = listener.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		//build(client);
		if(client==null)return;

		processedDataList.add(preProcess(client));
	}
	public <T> T getData(){
		if(processedDataList.isEmpty())return null;
		T output=(T)processedDataList.get(0);
		processedDataList.remove(0);
		System.out.println(processedDataList.size());
		return output;
	}
	
	protected DatagramPacket reviceUDPPackage(){
		DatagramPacket output=outputUDPStock.get(0);
		outputUDPStock.remove(0);
		return output;
	}
	@Override
	protected void reviceUDP() throws IOException{
		byte[] buff=new byte[4096];
		DatagramPacket inputUDP=new DatagramPacket(buff,buff.length);
		ClientUDP.receive(inputUDP);  
		outputUDPStock.add(inputUDP);
		DatagramPacket outputUDP= new DatagramPacket("OK".getBytes(),"OK".length(),ip,port); 
		ClientUDP.send(outputUDP);
	}
	@Override
	protected void sendUDP() throws IOException{
		DatagramPacket outputUDP=inputUDPStock.get(0);
		inputUDPStock.remove(0);
		byte[] buff=new byte[4096];
		DatagramPacket inputUDP=new DatagramPacket(buff,buff.length);
		for(int i=0;i<5;i++){
			ClientUDP.send(outputUDP);
			ClientUDP.receive(inputUDP);
			if(inputUDP.getAddress().equals(ip)){
				String respone=new String(inputUDP.getData());
				if(respone.equals("OK"))break;
				else outputUDPStock.add(inputUDP);
			}
		}
	}
	public void sendUDPData(DatagramPacket data) throws IOException{
		inputUDPStock.add(data);
		sendUDP();
	}
	
	public abstract<T> T preProcess(T data);
	public abstract void listenUndefine();
}
