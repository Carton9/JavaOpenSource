package adv.Web;

import java.io.IOException;

public abstract class WebServer extends Thread implements WebInterface{

	@Override
	public void init(Protocol type, String IP, int port) throws UndefineProtocolException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(byte[] data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] revice() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
