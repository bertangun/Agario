import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class Server {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	static int ClietnBallX,ClietnBallY;
	static int ClientScore;
	
	static public ArrayList<Food> Serverf = new ArrayList<Food>();//Foodlarý tuttuðum array list...
	
	
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, UnknownHostException {
		Server app = new Server();
		

		Source soo=new Source();
		Source.WhoAreYou=true;
	
		
		app.runServer();

	}
	
	public Server() throws InterruptedException{
		
		CreateFoodandNonFood(200,(short)0);//Create Foods
		CreateFoodandNonFood(30,(short)1);//Create NonFoods
	

	}
	
	
	public void runServer() throws ClassNotFoundException{
		try {
			server = new ServerSocket(1113);
			
			while(true){
				try {
					
					FrameClass.f=Server.Serverf;
					waitForConnection();
					getStreams();
					processConnection();
				} catch (EOFException e) {
					System.out.println("\nServer terminated connection");
				} finally{
					closeConnection();
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	 void waitForConnection() throws IOException, ClassNotFoundException{
		System.out.println("Waiting for connection");
		connection = server.accept();
		System.out.println("\nConnection " +" received "	+ "from: " 	+ connection.getInetAddress().getHostName());
	}
	 
	 
	
	 void getStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		System.out.println("\nGot I/O streams\n");
	}
	 
	 
	
	 void processConnection() throws IOException, ClassNotFoundException{
		
		 
		 FrameClass.f=Server.Serverf;
			sendData(Server.Serverf);//Foodlarýn cliente gitmesi için...
			System.out.println("Gönderdiðim altta...");
		do{
			
			sendScore(FrameClass.b.size);//sent balsize, high scor için....
			sendServerBallXY(ScrollPaneClass.xx);
			sendServerBallXY(ScrollPaneClass.yy);
			
			//System.out.println("x= " + ScrollPaneClass.xx + " y= " + ScrollPaneClass.yy);

			ClientScore= (int) input.readObject();//Clientin score çektik.
			ClietnBallX=((int) input.readObject())+((int)FrameClass.ScreenSize.width/2)-((int)ClientScore/2);
			ClietnBallY=((int) input.readObject())+((int)FrameClass.ScreenSize.height/2)-((int)ClientScore/2);
			
			
			//System.out.println(ClientScore);
		}while(true);
	}
	
	 
	 
	private void closeConnection(){
		System.out.println("\nTerminating connection\n");
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	void sendData(ArrayList<Food> serverf2){
		try {
			output.writeObject(serverf2);
			output.flush();
			//System.out.println("\nSERVER>>> " + message);
		} catch (Exception e) {
			// TODO: handle exception
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
	

static void CreateFoodandNonFood(int Number,short type)
	{
			//Foodlarý oluþturduðum yer...-------
			for(int i=0;i<Number;i++)
			{
				Random R=new Random();
		    	int randX = R.nextInt(3080);
		    	int randY = R.nextInt(3440);
		    	Food d = new Food(randX,randY,20,(short)10,type);
		    	Serverf.add(d);
			}
			//------------------------------------
		
	}



	
	
	 
}





