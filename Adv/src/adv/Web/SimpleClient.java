package adv.Web;

import java.io.IOException;

public class SimpleClient extends WebClient {
	
	public SimpleClient(Protocol type, String IP, int port) throws UndefineProtocolException, IOException {
		super(type, IP, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initUndefine(String ip, int port) throws UndefineProtocolException, IOException {
		// TODO Auto-generated method stub
		throw error;
	}

	@Override
	public void buildUndefine(Object Client) throws UndefineProtocolException, IOException {
		// TODO Auto-generated method stub
		throw error;
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
