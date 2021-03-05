package controler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import view.Login;

public class Main {
	
	public static Socket socket = null;
	
	public static void main(String[] args) 
	{
		 String host = "127.0.0.1";
		 int port = 3302;
		
		try{
		    File f = new File ("config.txt");
		    
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try
		    {
		        String line = br.readLine();
		        
		        int i = 0;
		        
		        while (line != null)
		        {
		        	if (i == 0)
		        		host = line.split("=")[1].trim();

		        	if (i == 1)
		        		port = Integer.valueOf(line.split("=")[1].trim());
	        		
		        	i++;
		        	
		            line = br.readLine();
		        }
		 
		    	try {
			         socket = new Socket(host, port);
					 	
					 System.out.println("Client: Connexion établie avec le serveur, authentification");
					 			
			         if (socket.isConnected()) {
						Login login = new Login(socket);
						login.setVisible(true);
			         }
				}
				catch(Exception e) {
				    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion au serveur, merci de bien suivre les inscructions du fichier readme", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
		    	
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception)
		    {
			    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion au serveur, merci de bien suivre les inscructions du fichier readme", "Erreur", JOptionPane.ERROR_MESSAGE);
		    }
		}
		catch (FileNotFoundException exception)
		{
		    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion au serveur, merci de bien suivre les inscructions du fichier readme", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
}

