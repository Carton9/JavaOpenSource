package adv.Web;

import java.io.IOException;

public interface WebInterface {
	public void init(Protocol type,String IP,int port)throws UndefineProtocolException, IOException;
	public void send(byte[] data);
	public byte[] revice();
	//public void serverStart(int port);
}
