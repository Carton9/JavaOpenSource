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

public abstract class WebListener extends WebService{
	private static HashMap<String,WebListener> ListenerList=new HashMap<String,WebListener>();
	private ArrayList processedDataList=new ArrayList();
	private ServerSocket listener;
	public WebListener(String name,String IP,Protocol type,int port) throws UndefineProtocolException, IOException{
		error=new UndefineProtocolException();
		init(type,IP,port);
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
		}else if(type==Protocol.UDP){
			try {
				listenTCP();
			} catch (IOException | UndefineProtocolException e) {
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
	private void listenUDP() throws IOException{
		reviceUDP();
		processedDataList.add(preProcess(revice()));
	}
	private void listenTCP() throws IOException, UndefineProtocolException{
		Socket client = listener.accept();
		build(client);
		processedDataList.add(preProcess(client));
	}
	public <T> T getData(){
		if(processedDataList.isEmpty())return null;
		T output=(T)processedDataList.get(0);
		processedDataList.remove(0);
		return output;
	}
	public abstract<T> T preProcess(T data);
	public abstract void listenUndefine();
}
