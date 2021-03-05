package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Model.ConnexionDB;
import view.Serveur;

public class Main {
	
	public static void main(String[] args) 
	{
		ConnexionDB connexionDB;
		
		
		String dbName ="projets5_grunivertaires";
		String user ="root";
		String password ="";
		String host = "localhost";
		int port = 3302;
		try	{
		    File f = new File ("config.txt");
		    
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader (fr);
		 
		    try {
		        String line = br.readLine();
		        
		        int i = 0;
		        
		        while (line != null)
		        {
		        	if (i == 0)
		        		dbName = line.split("=")[1].trim();

		        	if (i == 1)
		        		user = line.split("=")[1].trim();
		        	
		        	if (i == 2)
		        		password = line.split("=")[1].trim();
		        	
		        	if (i == 3)
		        		host = line.split("=")[1].trim();
		        	
		        	if (i == 4)
		        		port = Integer.valueOf(line.split("=")[1].trim());

		        	i++;
		        	
		            line = br.readLine();
		        }
		 
		    	try {
					 
		    		connexionDB = new ConnexionDB(dbName,user,password);
					Serveur serveur = new Serveur(connexionDB);
					serveur.setVisible(true);
					
					initServeur(serveur.ecran,connexionDB,host,port);
				}
				catch(Exception e) {
				    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donée, merci de bien suivre les inscructions du fichier readme", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
		    	
		        br.close();
		        fr.close();
		    }
		    catch (IOException exception)
		    {
			    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donée, merci de bien suivre les inscructions du fichier readme", "Erreur", JOptionPane.ERROR_MESSAGE);
		    }
		}
		catch (FileNotFoundException exception)
		{
		    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de donée, merci de bien suivre les inscructions du fichier readme", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void initServeur(JTextArea ecran,ConnexionDB connexionDB,String host,int port) {
		 ProcessusDuServeur ts = new ProcessusDuServeur(host, port,ecran,connexionDB);
		 ts.open();
	}
}
