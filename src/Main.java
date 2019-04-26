
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main {

	
	//testing the CMD interactions, but it doesn't work as intended
	public static void testCMD()
	{
		try {
	        Runtime.getRuntime().exec("cmd.exe /c start");
	        System.out.println("opened");
	        
	        Process p = Runtime.getRuntime().exec("cmd /C dir");
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
	        
	        Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
	        System.out.println("closed");
	        
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		System.out.println("cmd stopped");

		/*
		ProcessBuilder pb = new ProcessBuilder("echo", "Hello World !");
        File log = new File("log");
        pb.redirectOutput(ProcessBuilder.Redirect.to(log));
        try {
            Process p = pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
		
	}
	
	
	public static void tryit()
	{
		try {
	        Runtime.getRuntime().exec("cmd.exe /c start cd /");
	        //\Users\PREDATOREL\Desktop\cbsl_test
	        Runtime.getRuntime().exec("cmd.exe /c start cd /");
	        System.out.println("opened");

            
            try {
    			TimeUnit.SECONDS.sleep(5);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
	        
	        Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
	        System.out.println("closed");
	        
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		System.out.println("cmd stopped");
		
		
	}
	
	
	//main program run
	public static void main(String argv[]) 
	{
		//create the world tile map
		WorldTileMap.run();
		
		//create the world creature map
		//WorldCreatureMap.run();
		
		//create the world entity map
		//WorldEntityMap.run();
		
		//tryit();
	}
}
