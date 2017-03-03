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
	WebClient listener=new WebClient(){

		@Override
		public void initUndefine(String ip, int port) throws UndefineProtocolException, IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void buildUndefine(Object Client) throws UndefineProtocolException, IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sendUndefine() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reviceUndefine() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void closeUndefine() throws IOException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
}
