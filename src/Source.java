


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFrame;




public class Source {
	
	static FrameClass fc;
	static boolean WhoAreYou;
	static int HighScore=0;
	public static void main(String[] args)  { 
	
	}
	
	
	public Source()
	{
		FrameClass fc=new FrameClass();
		fc.setVisible(true);
		fc.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fc.setTitle("BAÜ Game...");
		//fc.setUndecorated(true);
		//bu alttakiler hep full screen için denemeler...
		//fc.setSize(800,800);
		//fc.setAlwaysOnTop(true);
        //fc.setResizable(false); 
		fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //(WindowConstants.EXIT_ON_CLOSE) bu seçenek de kullanýlabilir aslýnda ama derste biz bunu görmedik...
		
		FrameClass.ScreenSize =fc.getSize(); //ekran boyutunu bu dimention deðiþkenine atýyorum ki ileride hem topun merkezde kalmasýný bu kod sayesinde hesaplayarak bulucam...
		
		System.out.println(FrameClass.ScreenSize.getWidth());
		System.out.println(FrameClass.ScreenSize.getHeight());
		TextFileReadingExample3 ();//read high score...
	}
	
	
	
	//----------------------------------------High Score-------------------------------------
	static public void TextFileReadingExample3 ()
	{
		// The name of the file to open.
        String fileName = "HighScore.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                HighScore=Integer.parseInt(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	   }
	 //------------------------------------------------------------------------------
	
	

	static public void TextFileWritingExample3(int HighScore) {
 
		// The name of the file to open.
        String fileName = "HighScore.txt";

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(HighScore);
    

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

	

	

}
