package adv.Web;

import java.io.IOException;
// fix
public class MainTest {
	static byte[] bb=new byte[4096];
	public static void main(String[] args) throws UndefineProtocolException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		WebClient testClient=new SimpleClient(Protocol.UDP,"127.0.0.1",12345);
		Thread a=new Thread(testClient);
		a.run();
		while(true){
			//a.suspend();
			byte[] buff=testClient.revice();
			if(buff==null){
				continue;
			}
			System.out.println(new String(buff));
			testClient.send("best client".getBytes());
			//a.resume();
		}
	}

}
