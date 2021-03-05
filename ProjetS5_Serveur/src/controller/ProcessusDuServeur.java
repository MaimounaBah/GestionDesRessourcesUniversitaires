package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JTextArea;
import Model.ConnexionDB;
public class ProcessusDuServeur {

	   //On initialise des valeurs par défaut
	   private int port = 3302;
	   private String host = "127.0.0.1";
	   private ServerSocket server = null;
	   private boolean isRunning = true;
	   
	   BufferedReader in;
	   PrintWriter out;

	   JTextArea ecran;
	   public ProcessusDuServeur(){
	      try {
	    	  
	         server = new ServerSocket(port, 100, InetAddress.getByName(host));
	         
	         this.ecran = new JTextArea();
	         
		      ecran.setText(ecran.getText()+"Serveur initialisé.\nPort: "+port+"\nAdresse IP: "+host);
		      
		      System.out.println("\nfghj\n");
		      
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   ConnexionDB connexionDB;
	   
	   public ProcessusDuServeur(String pHost, int pPort,JTextArea ecran,ConnexionDB connexionDB) {
	      host = pHost;
	      port = pPort;
	      
	      this.connexionDB = connexionDB;
	      
	      try {
	         server = new ServerSocket(port);
	         
	         this.ecran = new JTextArea();
	         
		      ecran.setText(ecran.getText()+"Serveur initialisé.\nPort: "+port+"\nAdresse IP: "+host);
		      
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   public void open(){
		      
		      Thread t = new Thread(new Runnable(){
		         public void run(){
		            while(isRunning == true){
		               
		               try {
		            	   	Socket client = server.accept();
		            	   	
		                  	System.out.println("Serveur: Connexion cliente reçue.");      
		                 	
		  		        	// Authentification du client
		  		        	Thread t = new Thread(new ProcessusAuthentification(client,connexionDB));
		  		        	t.start();
		                  
		               } catch (IOException e) {
		                  e.printStackTrace();
		               }
		            }
		            
		            try {
		               server.close();
		            } catch (IOException e) {
		               e.printStackTrace();
		               server = null;
		            }
		         }
		      });
		      
		      t.start();
		   }
	   
	   public void close(){
	      isRunning = false;
	   }   
	}




