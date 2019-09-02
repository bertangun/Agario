import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.SoundbankResource;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;



public class ScrollPaneClass extends JScrollPane {

	
	
	static JViewport jv;
	JLabel background;
	ImageIcon img;
	static int xx=0,yy=0;
	Timer timer1,timer2;
	static int time=0;
	int PowerFoodIndex, PowerFoodStartTime;
    
	
	ScrollPaneClass()
	{
		
		jv=new JViewport();
		jv.setViewPosition(new Point(xx, yy));
		
		
		img =new ImageIcon("background.jpg");
		background=new JLabel(img);
		jv.add(background);
		
		add(FrameClass.b);
		

		Food d = null;
		//--------Time göstergesi için gereken timer--------
		TimerTask CornerMove = new TimerTask() {
			@Override
			public void run() {
				
				time++;
			
				
			//------------------Power Ups------------------------------------------------
//			her 20 saniyede bir 19 saniye ekranda kalýr ve 1 saniye sonra yeni bir power up üretir... 
//			Eðer arasýný açmak istersen burayý 50 yap mesela, aþþaðýyý 19 býrak 50 saniyede bi çýkar 19 saniye kalýr 30 saniye bekler birdaha çýkmak için...
			if(time%10==0) 
			{	
				
				PowerFoodStartTime=time;
				Random R=new Random();
				int randX = R.nextInt(3080);
				int randY = R.nextInt(3440);
				Food d = new Food(randX,randY,50,(short)100,(short) 2);
		    	Server.Serverf.add(d);
		    	Client.Clientf.add(d);
		    	
			}
			//--------------------------------------------------------------
			
			if(time==PowerFoodStartTime+9 )
			{	  for(int i=0;i<Server.Serverf.size();i++)
					if(Server.Serverf.get(i).FoodType==2)
						Server.Serverf.remove(i);// bu remove yaptýðý foodu servera göndermeli....
				
				for(int i=0;i<Client.Clientf.size();i++)
					if(Client.Clientf.get(i).FoodType==2)
						Client.Clientf.remove(i);
			}
			
			}};
		timer1 = new Timer(); //Timer 1 oluþturuldu...
		timer1.schedule(CornerMove, 0,1000);
		//---------------------------------------------------------------------------------
		
     
	}
	
	public void paint(Graphics g)
	{  	Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		g2d.setColor(FrameClass.c);
		
		
		//g2d.drawString("XX : "+xx +" YY : "+ yy + " Score : " + FrameClass.score, 10, 70);//GEÇÝCÝ OLARAK KOYDUM SÝLÝNECEK BU 
		
		
		g2d.setColor(FrameClass.c);
		FrameClass.b.paint(g2d); //ballu ScrolPaneClass 
		
		
		//FOOD paint etmek için gereken koddur... f adýndaki food list arrayinin içindeki eleman sayýsý kadar çizim yapar
		for(int i=0;i<FrameClass.f.size();i++)
			FrameClass.f.get(i).paint(g2d);

		
		//---------------------INFO SCREEN---------------------
				g2d.setColor(new Color(204,0,0));
				g2d.setFont(new Font("OCR A Extended", Font.BOLD, 20));
				g2d.drawString("Speed: " + FrameClass.b.fastly , 10, 20);
				g2d.drawString("Weight: " + FrameClass.b.size , 10, 40);
				g2d.drawString("Time: " +time , 10, 60);
				//-----------------------------------------------------
			
				
				//--------------HIGH SCORE----------------------------
				g2d.setFont(new Font("OCR A Extended", Font.BOLD, 20));
				g2d.setColor(new Color(204,0,0));
				if(Source.WhoAreYou==true)
					{if(FrameClass.b.size>Server.ClientScore)
						g2d.drawString("RECENT GAME HIGH SCORE: " + FrameClass.b.size , 1500, 40);
					else
						g2d.drawString("RECENT GAME HIGH SCORE: " + Server.ClientScore , 1500, 40);
					}
				else
				{
					{if(FrameClass.b.size>Client.ServerScore && Client.WaitingForConnection==true)
						g2d.drawString("RECENT GAME HIGH SCORE: " + FrameClass.b.size , 1500, 40);
					else
						g2d.drawString("RECENT GAME HIGH SCORE: " + Client.ServerScore , 1500, 40);
					}
				}
				
				
				if(Source.HighScore<FrameClass.b.size)
				{
					Source.HighScore=(int)FrameClass.b.size;
					
					//Source.TextFileReadingExample3();//read high score...
				}
				
				g2d.setColor(new Color(0,0,204));
				g2d.drawString("GAME GLOBAL HIGH SCORE: " + Source.HighScore  , 1500, 20);
				//---------------------------------------------------
    	

			
		
		repaint();	
	}
	
	
	
}
