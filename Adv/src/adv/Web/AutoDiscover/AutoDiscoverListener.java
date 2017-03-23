package adv.Web.AutoDiscover;

import java.io.IOException;

import adv.Web.*;

public class AutoDiscoverListener {
	class listener extends WebService{
		String boardCasting;
		public listener(String IP,int port) throws UndefineProtocolException, IOException{
			error=new UndefineProtocolException();
			init(Protocol.UDP,IP,port);
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
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
	}

	public AutoDiscoverListener(String IP,int port) {
		
		
	}

}
