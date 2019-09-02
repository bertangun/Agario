

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ball extends JPanel{
    public int x;
    public int y;
    public int size;
    public Color color;
    public short fastly;
    public String ballName;
    static Timer timer1, timer2;
    Graphics2D g2d;
    static boolean StopGame=false;
    //public static Blob blob = new Blob(800,100,40,Color.blue);
   

    Ball( int size,short fastly){
        this.size = size;
        this.color = new Color(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)); //Bu her defasýnda rengi deðiþsin olayýnýn basic hali bu deðiþicek User classýna atama yapacak ki karþý kullanýcý da gerçek renkte görsün rakibini...
        this.fastly=fastly;
     
    }
    int i=0;
    public void paint(Graphics g){
    	 g2d = (Graphics2D) g;
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //bu en önemli þeylerden bir tanesi benim için... Grafiksel anlamda bitmap görünümü panele daðýtýyor...
    	super.paint(g);
    	g2d.setColor(color);
    	g2d.fillOval((int)(FrameClass.ScreenSize.getWidth()/2)-size/2, (int)(FrameClass.ScreenSize.getHeight()/2)-size/2, size, size);  //921 ve 514 screenin yarýsý anlamýnda tam merkezi saptamak amaçlý
    	
    	//------------------------Top üzerindeki Kullanýcý adý yazýsý...-------------------------------
    	g2d.setColor(Color.WHITE);
    	g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
    	g2d.drawString(ballName,(int)(FrameClass.ScreenSize.getWidth()/2)-ballName.length()*4,(int)(FrameClass.ScreenSize.getHeight()/2)-size/2+size/2+5);
    	//--------------------------------------------------------------------------------------------
    	
    	
    	
    	///////-----------------------------KARIÞI OYUNCUNUN TOPUNU EKRANA DRAW ETTÝÐÝM YERDÝR---------------------------------------------
			if(Source.WhoAreYou==true)
			{
				g2d.setColor(Color.BLACK);
				g2d.fillOval(Server.ClietnBallX-ScrollPaneClass.xx, Server.ClietnBallY-ScrollPaneClass.yy, Server.ClientScore, Server.ClientScore);  //921 ve 514 screenin yarýsý anlamýnda tam merkezi saptamak amaçlý
			}
		
			if(Source.WhoAreYou==false)
			{
				g2d.setColor(Color.BLACK);
				g2d.fillOval(Client.ServerBallX-ScrollPaneClass.xx, Client.ServerBallY-ScrollPaneClass.yy, Client.ServerScore, Client.ServerScore);  //921 ve 514 screenin yarýsý anlamýnda tam merkezi saptamak amaçlý
			}
	    ///////--------------------------------------------------------------------------------------
			if(Source.WhoAreYou==true)
				EatBallServer();
			else
				EatBallClient();
			
    	repaint();
    }
    
    
    public void EatBallServer() 
    {
    	if( ((Server.ClietnBallX-ScrollPaneClass.xx<((int)(FrameClass.ScreenSize.getWidth()/2)+FrameClass.b.size/2)) 	&& 	(Server.ClietnBallX-ScrollPaneClass.xx>((int)(FrameClass.ScreenSize.getWidth()/2)-FrameClass.b.size/2))
        		||
        	(Server.ClietnBallX-ScrollPaneClass.xx+20<((int)(FrameClass.ScreenSize.getWidth()/2)+FrameClass.b.size/2)) 	&& 	(Server.ClietnBallX-ScrollPaneClass.xx+20>((int)(FrameClass.ScreenSize.getWidth()/2)-FrameClass.b.size/2)))
        		&&
        	(((Server.ClietnBallY-ScrollPaneClass.yy+20<((int)(FrameClass.ScreenSize.getHeight()/2)+FrameClass.b.size/2)) 	&& 	(Server.ClietnBallY-ScrollPaneClass.yy+20>((int)(FrameClass.ScreenSize.getHeight()/2)-FrameClass.b.size/2)))
        		||
        	((Server.ClietnBallY-ScrollPaneClass.yy<((int)(FrameClass.ScreenSize.getHeight()/2)+FrameClass.b.size/2)) 	&& 	(Server.ClietnBallY-ScrollPaneClass.yy>((int)(FrameClass.ScreenSize.getHeight()/2)-FrameClass.b.size/2))))
        		)
    		
    		
        	{
    		

    		if(Source.HighScore<FrameClass.b.size)
			{
				Source.HighScore=FrameClass.b.size;
				Source.TextFileWritingExample3(Source.HighScore);
			}
    		
    		g2d.setColor(Color.RED);
        	g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 150));
    				if(Server.ClientScore>FrameClass.b.size)
    				{
    					g2d.drawString("GAME OVER...",(int)(FrameClass.ScreenSize.getWidth()/2)-400,(int)(FrameClass.ScreenSize.getHeight()/2));
    					repaint();
    					
    					
    				}
    				else
    				{
    					g2d.drawString("YOU ARE WÝNNER...",(int)(FrameClass.ScreenSize.getWidth()/2)-800,(int)(FrameClass.ScreenSize.getHeight()/2));
    					repaint();
    				
    				
    				}
    				StopGame=true;
        	}
    }
    
    
    public void EatBallClient() 
    {
    	if( ((Client.ServerBallX-ScrollPaneClass.xx<((int)(FrameClass.ScreenSize.getWidth()/2)+FrameClass.b.size/2)) 	&& 	(Client.ServerBallX-ScrollPaneClass.xx>((int)(FrameClass.ScreenSize.getWidth()/2)-FrameClass.b.size/2))
        		||
        	(Client.ServerBallX-ScrollPaneClass.xx+20<((int)(FrameClass.ScreenSize.getWidth()/2)+FrameClass.b.size/2)) 	&& 	(Client.ServerBallX-ScrollPaneClass.xx+20>((int)(FrameClass.ScreenSize.getWidth()/2)-FrameClass.b.size/2)))
        		&&
        	(((Client.ServerBallY-ScrollPaneClass.yy+20<((int)(FrameClass.ScreenSize.getHeight()/2)+FrameClass.b.size/2)) 	&& 	(Client.ServerBallY-ScrollPaneClass.yy+20>((int)(FrameClass.ScreenSize.getHeight()/2)-FrameClass.b.size/2)))
        		||
        	((Client.ServerBallY-ScrollPaneClass.yy<((int)(FrameClass.ScreenSize.getHeight()/2)+FrameClass.b.size/2)) 	&& 	(Client.ServerBallY-ScrollPaneClass.yy>((int)(FrameClass.ScreenSize.getHeight()/2)-FrameClass.b.size/2))))
        		)
        	{
    		
    		if(Source.HighScore<FrameClass.b.size)
			{
				Source.HighScore=FrameClass.b.size;
				Source.TextFileWritingExample3(Source.HighScore);
			}
    		
    		g2d.setColor(Color.RED);
        	g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 150));
    				if(Client.ServerScore>FrameClass.b.size)
    				{
    					g2d.drawString("GAME OVER...",(int)(FrameClass.ScreenSize.getWidth()/2)-400,(int)(FrameClass.ScreenSize.getHeight()/2));
    					repaint();
    				
    					
    				}
    				else
    				{
    					g2d.drawString("YOU ARE WÝNNER...",(int)(FrameClass.ScreenSize.getWidth()/2)-800,(int)(FrameClass.ScreenSize.getHeight()/2));
    					repaint();
    				
    				
    				}
    				StopGame=true;
        	}
    }
    
    
    
}