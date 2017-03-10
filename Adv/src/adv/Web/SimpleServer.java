package adv.Web;

import java.net.DatagramPacket;
//fix
public class SimpleServer extends WebServer {

	public SimpleServer(String name, Protocol type, String IP, int port) {
		super(name, type, IP, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] process(byte[] data) {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public DatagramPacket process(DatagramPacket data) {
		// TODO Auto-generated method stub
		return data;
	}

}
