package adv.Web;

import java.io.IOException;
// fix
public class MainTest {
	static byte[] bb=new byte[4096];
	public static void main(String[] args) throws UndefineProtocolException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		WebClient testClient=new SimpleClient(Protocol.UDP,"192.168.182.128",12345);
		Thread a=new Thread(testClient);
		testClient.send("best client".getBytes());
		a.start();
		int i=0;
		while(true){
			System.out.println(testClient.inputStock.size());
			//a.suspend();
			byte[] buff=testClient.revice();
			if(buff!=null){
				System.out.println(new String(buff));
			}
			String a1=i+"";
			testClient.send(a1.getBytes());
			
			
			i++;
			Thread.sleep(100);
			//a.resume();
		}
	}

}
