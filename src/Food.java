import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;


public class Food  extends JPanel{
	public int FoodX;
	public int FoodY;
	public int FoodSize;
	public short FoodValue;
	public short FoodType;

	
	Food(int FX,int FY,int FSize,short FValue,short FType)
	{
		FoodX=FX;
		FoodY=FY;
		FoodSize=FSize;
		FoodValue=FValue;
		FoodType=FType;
	}
	

	
	public void paint (Graphics g)
	{
		Graphics2D g2d=(Graphics2D) g;
		super.paint(g);
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); //bu en �nemli �eylerden bir tanesi benim i�in... Grafiksel anlamda bitmap g�r�n�m� panele da��t�yor...
		
		
		//Burda yeme�in tipine g�re renklendirme i�lemi yap�l�yor...
		if(FoodType==0)
		g2d.setColor(Color.GREEN);
		else if(FoodType==1)
		g2d.setColor(Color.RED);
		else
		g2d.setColor(Color.YELLOW);
		//-----------------------------------------------------------
		
		
		g2d.fillOval(FoodX-ScrollPaneClass.xx,FoodY-ScrollPaneClass.yy, FoodSize, FoodSize);
		
		
	
		repaint();
	}

}
