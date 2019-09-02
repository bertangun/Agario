import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.rmi.server.ServerCloneException;
import java.rmi.server.ServerRef;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;



public class FrameClass extends JFrame implements MouseMotionListener,ActionListener,KeyListener  {

	static JScrollPane sp;
	static Point ViewPoint;
	static JPanel SettingsPanel;//setting ekran� i�in ekledim...
	
	
	static int MouseXLocation = 0;
    static int MouseYLocation = 0;
    static int score = 0;
    static Color c;
    static Dimension ScreenSize; //Ekran�n boyutunu al�yorum ki topun merkezde olmas�n� ve hareketleri bu sayede hesapl�yabiliyorum...
   
   
    static Ball b = new Ball( 100,(short)10);//Main ball
	static public ArrayList<Food> f = new ArrayList<Food>();//Foodlar� tuttu�um array list...
	Timer timer2;
    
    
    //------Settings Objects---------
    JLabel NameLabel;
    JTextField NameTextField;
    JCheckBox AgeCheck;
    JButton StartButton;
    ImageIcon img;
    JLabel background;
  
    //-------------------------------
   static int i=0;
   boolean Up, Down, Right,Left; //multi key press i�in ekledim...
	
   
   //--------------------SERVER---------------------------------
   //static Server Server=new Server();
   //----------------------------------------------------------
   
public FrameClass()
{
	
	this.setFocusable(true);//Key listener �al��m�yordu bende Frame e bi focus yapt�m... Hocayla g�r��t�k o sayede ��z�mledim...
	
	settings();
	
	AgeCheck.addActionListener(this);//Bu neden burda dersen... �nce check box olu�tuman gerek Onuda   Settings adl� fonksiyonda yap�yor sonra bu atamay� yap�yorum aksi halde �al��maz...
	StartButton.addActionListener(this);//yukardaki a��klamann�n ayn�s�... SAn�r�m biraz
	
	

}




public void StartGame() throws ClassNotFoundException, IOException //Bu fonksiyon 13 ya� s�n�r� i�aretlenip butona bas�l�nca scrolpane i olu�turucak...Temel oyunu olu�turan yap�d�r bence...
{
	//-------Bu alttakiler s�rf panel ve scrole pane birde frame ba�lant�s�n� sa�lamak �zerine gerekli eklemeler... �� i�e matru�ka gibi eklemem gerekti...
		sp=new ScrollPaneClass();
		sp.setViewport(ScrollPaneClass.jv);
		add(sp);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVisible(true); //neden false ��nk� �nce setting panel gelsin ondan sonra hallederiz...
		//----------------------------------------------------------------------------------------------------------------------------------------------------
		
	
		
		sp.addMouseMotionListener(this);	
		
}
	

public void settings()
{
	SettingsPanel =new JPanel();
	SettingsPanel.setVisible(true);
	SettingsPanel.setLayout(null); //bunu yapmazsam location ayarlamama izin vermez �zerindeki nesnelerin...
	addKeyListener(this);
	
	
	
	//------------------NAME LABEL----------------
	NameLabel=new JLabel();
	NameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
	NameLabel.setForeground(Color.WHITE);
	NameLabel.setText("PLEASE ENTER YOUR N�CK NAME");
	NameLabel.setVisible(true);
	NameLabel.setBounds(700, 200, 600, 40);
	//NameLabel.setBorder(BorderFactory.createLineBorder(Color.black)); //lable �er�evesini �izer ama gerek yok �imdilik buna
	SettingsPanel.add(NameLabel);
	//---------------------------------------------
	
	
	//-----------------NAME TEXTFIELD---------------
	NameTextField =new JTextField();
	NameTextField.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
	NameTextField.setVisible(true);
	NameTextField.setBounds(715, 300, 400, 40);
	NameTextField.setHorizontalAlignment(JTextField.CENTER);
	SettingsPanel.add(NameTextField);
	//----------------------------------------------
	
	
	//-----------AGE CONTROL CHECKBOX---------------
	AgeCheck = new JCheckBox();
	AgeCheck.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
	AgeCheck.setForeground(Color.RED);
	AgeCheck.setText("I am over than 13");
	AgeCheck.setVisible(true);
	AgeCheck.setBounds(830, 400, 175, 40);
	AgeCheck.setBackground(Color.WHITE);
	SettingsPanel.add(AgeCheck);
	//----------------------------------------------
	
	
	//----------START BUTTON------------------------
	StartButton=new JButton();
	StartButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
	StartButton.setForeground(Color.blue);
	StartButton.setText("START GAME");
	StartButton.setVisible(false);
	StartButton.setBounds(817, 550, 200, 100);
	StartButton.setHorizontalAlignment(JTextField.CENTER);
	SettingsPanel.add(StartButton);
	//----------------------------------------------
	
	
	//-----------------SETT�NGS PANEL BACKGROUND---------------------------
	img =new ImageIcon("SettingsScreen.jpg");
	background=new JLabel(img);
	background.setVisible(true);
	background.setBounds(-40, -470,2000,2000); //bu de�erler neden - dersen resim boyutu 16:9 oldu�u i�in k��eye gelsin die eksiden ba�latt�m
	SettingsPanel.add(background);
	//--------------------------------------------------------------------
	
	
	add(SettingsPanel);
	
	
}



@Override
public void mouseDragged(MouseEvent e) {}
@Override
public void mouseMoved(MouseEvent e) {	
    
	if(Ball.StopGame!=true)
	{ 	
		MouseXLocation = e.getX();
    	MouseYLocation = e.getY();
	
    
 
    //------------------------------------------------------MOUSE KONTROLLER�-----------------------------------------
    if(MouseXLocation<(ScreenSize.getWidth()/2)+20  && ScrollPaneClass.xx>-(ScreenSize.getWidth())/2+b.size/2)  //b.size/2 nin amac� mousu tam pointin ortas�na getirtmek...//+20 olmas�n�n nedeni o d�z gitme aral���na 40 verdim +20 burda -20 de altta
    {	
    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx-=b.fastly,ScrollPaneClass.yy));
    }
    
    if(MouseXLocation>(ScreenSize.getWidth()/2)-20 && ScrollPaneClass.xx<(ScreenSize.getWidth()+300)-b.size/2)//+20 olmas�n�n nedeni o d�z gitme aral���na 40 verdim +20 burda -20 de altta  neden +300 derseniz orda bi orant�s�zl�k oluyor bende her defas�nda +300 � eklersem ekran tam oranlan�yor deneme ile buldum
    {
    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx+=b.fastly,ScrollPaneClass.yy));
    }
    
    if(MouseYLocation<(ScreenSize.getHeight()/2)+20 && ScrollPaneClass.yy>-(ScreenSize.getHeight())/2+b.size/2) //+20 olmas�n�n nedeni o d�z gitme aral���na 40 verdim +20 burda -20 de altta
    {
    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx,ScrollPaneClass.yy-=b.fastly));
    }
    
    if(MouseYLocation>(ScreenSize.getHeight()/2)-20  && ScrollPaneClass.yy<2915-b.size/2)//+20 olmas�n�n nedeni o d�z gitme aral���na 40 verdim +20 burda -20 de altta nedne 2915 ��nk� o tam s�n�r �izgisi... deneme ile bulundu
    {
    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx,ScrollPaneClass.yy+=b.fastly));
    }
    //---------------------------------------------------------------------------------------------------------------
    
    MouseAndKeyCommonEvents();//�NEML�����
	}
}




//Buralar key eventi i�in yazd���m yerler... Yoruldum gece 2 olsu saat...
@Override
public void keyReleased(KeyEvent k) {
	switch(k.getKeyCode()) {
    case KeyEvent.VK_UP: Up = false; break;
    case KeyEvent.VK_DOWN: Down  = false; break;
    case KeyEvent.VK_LEFT: Left = false; break;
    case KeyEvent.VK_RIGHT: Right = false; break;
}
    
}
@Override
public void keyPressed(KeyEvent k) {
	 switch(k.getKeyCode()) {
	  case KeyEvent.VK_UP: Up = true; break;
	    case KeyEvent.VK_DOWN: Down  = true; break;
	    case KeyEvent.VK_LEFT: Left = true; break;
	    case KeyEvent.VK_RIGHT: Right = true; break;
 }
	 if(Ball.StopGame!=true)
	 {
		 //------------------------------------------------------MOUSE KONTROLLER�-----------------------------------------
	    if(Right==true && ScrollPaneClass.xx<(ScreenSize.getWidth()+300)-b.size/2)    {    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx+=b.fastly*2,ScrollPaneClass.yy));    }
	    if(Left==true && ScrollPaneClass.xx>-(ScreenSize.getWidth())/2+b.size/2){    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx-=b.fastly*2,ScrollPaneClass.yy));    }
	    if(Down==true && ScrollPaneClass.yy<2915-b.size/2)     {    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx,ScrollPaneClass.yy+=b.fastly*2));    }
	    if(Up==true && ScrollPaneClass.yy>-(ScreenSize.getHeight())/2+b.size/2)    {    	ScrollPaneClass.jv.setViewPosition(new Point(ScrollPaneClass.xx,ScrollPaneClass.yy-=b.fastly*2));    }
	  
	    //---------------------------------------------------------------------------------------------------------------
	   
	    MouseAndKeyCommonEvents();//�NEML�����
	 }
}
@Override
public void keyTyped(KeyEvent arg0) {}






//tekrar tekrar yazmamak ad�na b�yle bir giri�imde bulundum... hem key hemde mouse motionda �a��r�cam...
public void MouseAndKeyCommonEvents()
{
    //0------------------------------------------------------FOOD KONTROLLER�-----------------------------------------
    for(int i=0;i<f.size();i++)
    {
    	if( ((f.get(i).FoodX-ScrollPaneClass.xx<((int)(FrameClass.ScreenSize.getWidth()/2)+b.size/2)) 	&& 	(f.get(i).FoodX-ScrollPaneClass.xx>((int)(FrameClass.ScreenSize.getWidth()/2)-b.size/2))
    		||
    	(f.get(i).FoodX-ScrollPaneClass.xx+20<((int)(FrameClass.ScreenSize.getWidth()/2)+b.size/2)) 	&& 	(f.get(i).FoodX-ScrollPaneClass.xx+20>((int)(FrameClass.ScreenSize.getWidth()/2)-b.size/2)))
    		&&
    	(((f.get(i).FoodY-ScrollPaneClass.yy+20<((int)(FrameClass.ScreenSize.getHeight()/2)+b.size/2)) 	&& 	(f.get(i).FoodY-ScrollPaneClass.yy+20>((int)(FrameClass.ScreenSize.getHeight()/2)-b.size/2)))
    		||
    	((f.get(i).FoodY-ScrollPaneClass.yy<((int)(FrameClass.ScreenSize.getHeight()/2)+b.size/2)) 	&& 	(f.get(i).FoodY-ScrollPaneClass.yy>((int)(FrameClass.ScreenSize.getHeight()/2)-b.size/2))))
    		)
    	{
    		System.out.println("Food eat..."+i + "  Value= " + f.get(i).FoodValue);
    		
    		//Server.Serverf.get(i).FoodX=11111111; //deneme yapt�m...
    		if(f.get(i).FoodType==1 && b.size>105)//E�er nonFood yerse size -5 d��er...
    		{
    			b.size-=5;
    		}
    		else if(f.get(i).FoodType==0)
    		{
    			b.size++;	
    		}
    		else if(f.get(i).FoodType==2)
    		{
    			b.size+=f.get(i).FoodValue;
    			//System.out.println(f.get(i).FoodValue + "-----------------------------------------------------"); //deneme kontrol ama�l� yazd�rd�m...
    		}
    		
    		//yani program server modda �al���yorsa git server�n foodlar�n� sil e�er clientte �al���yorsa git clientin foodlar�n� fil
    		if(Source.WhoAreYou==true)
    			Server.Serverf.remove(i);// bu remove yapt��� foodu servera g�ndermeli....
    		else
    			Client.Clientf.remove(i);
    	
    	}
    }
    //---------------------------------------------------------------------------------------------------------------
    
 
    //0--------------------------------------------------FOOD AZALDIYSA EKLEME YAP-------------------------------------
    if(f.size()<200) //food say�s� 100 den az olursa 20 nonfood,200 de food ekletiyorum (say� de�erleri oynan�� durumuna g�re de�i�ebilir...)
    {
    	Server.CreateFoodandNonFood(200,(short)0);
    	Server.CreateFoodandNonFood(20,(short)1);
    }
    //---------------------------------------------------------------------------------------------------------------
    
    //O--------------------------------------------------BALL S�ZE CHECK AND FASTLY CHANGE HERE-----------------------
    if(b.size<100){b.fastly=6;}
    else if(b.size<200){b.fastly=5;}
    else if(b.size<300){b.fastly=4;}
    else if(b.size<400){b.fastly=3;}
    else if(b.size<500){b.fastly=2;}
    else if(b.size<600){b.fastly=1;}
    //-------------------------------------------------------------------------------------------------------------
}












//ActionPerformed zaten bildi�im gibi Frame i�i acionlar falanlar filanlar... :D
@Override
public void actionPerformed(ActionEvent e) {
	
	//-----------Age checkbox control---------------
	if(e.getSource()==AgeCheck)
	{
		if(AgeCheck.isSelected()==true)
		{
			StartButton.setVisible(true);
		}
		else
		{
			StartButton.setVisible(false);
		}	
	}
	//----------------------------------------------
	
	
	//--------------Button Start event---------------
	if(e.getSource()==StartButton)//Oyunun ba�lamas� i�in butona bas�lmas� gerekir mant�ken... �uan buralarada baya s�k�ld�m ondan sa�ma yorum sat�rlar� at�om gibi geldi... :D
	{	b.ballName=NameTextField.getText();
		SettingsPanel.setVisible(false);
		try {
			StartGame();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//-----------------------------------------------

}







}









