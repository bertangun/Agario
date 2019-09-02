import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


import javax.swing.JPanel;

public class Client extends JPanel {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket client;
	static int ServerBallX,ServerBallY;
	static int ServerScore;
	static boolean WaitingForConnection=false;

	
	static public ArrayList<Food> Clientf = new ArrayList<Food>();//Foodlarý tuttuðum array list...

	
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
		Client app = new Client();
	
		Source soo=new Source();//Start game...
	
		app.runClient();
		
		
	}
	
	

	
	public void runClient() throws InterruptedException, ClassNotFoundException{
		try {
			//FrameClass.f=Client.Clientf;
			connectToServer();
			getStreams();
			processConnection();
		} catch (EOFException e) {
			System.out.println("\nClient terminated connection");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			closeConnection();
		}
	}
	
	public void connectToServer() throws IOException{
		System.out.println("Attempting connection");
		client = new Socket("127.0.0.1", 1113); //InetAddress.getByName(InetAddress.getLocalHost().getHostAddress())
		System.out.println("\nConnected to : " + client.getInetAddress().getHostName());
		WaitingForConnection=true;		
	}
	
	public void getStreams() throws IOException{
		output = new ObjectOutputStream(client.getOutputStream());
		output.flush();
		
		input = new ObjectInputStream(client.getInputStream());
		System.out.println("\nGot I/O streams\n");
	}
	
	public void processConnection() throws IOException, ClassNotFoundException{
		
		//Clientin ilk toplarýný burada yerleþtiriyorum Serverdan çekere...
		Client.Clientf =null;
		Client.Clientf = (ArrayList<Food>) input.readObject(); //gelen mesajý burda alýyorum.................
		FrameClass.f=Client.Clientf;
		
		do{
			ServerScore=((int) input.readObject());
			ServerBallX=((int) input.readObject())+((int)FrameClass.ScreenSize.width/2)-((int)ServerScore/2);
			ServerBallY=((int) input.readObject())+((int)FrameClass.ScreenSize.height/2)-((int)ServerScore/2);
			
			//System.out.println("x= " + ServerBallX + " y= " + ServerBallY);
			
			sendScore(FrameClass.b.size);//sent balsize, high scor için....
			sendServerBallXY(ScrollPaneClass.xx);
			sendServerBallXY(ScrollPaneClass.yy);
			
			
			
			
	}while(true);

	}
	
	public void closeConnection(){
		System.out.println("\nClosing connection\n");
	
		
		try {
			output.close();
			input.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendScore(int size){
		try {
			output.writeObject(size);
			output.flush();
			//System.out.println("\nCLIENT>>> " + message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void sendServerBallXY(int Location){
		try {
			output.writeObject(Location);
			output.flush();
			//System.out.println("\nCLIENT>>> " + message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
}





