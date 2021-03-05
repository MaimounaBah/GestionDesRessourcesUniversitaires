package controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import Model.ConnexionDB;
import Model.GroupeDB;
import Model.ActionServeur;
import Model.LoginDB;
import Model.SSocket;
import Model.Utilisateur;
import Model.UtilisateurDB;

public class ProcessusAuthentification implements Runnable { 
	  private Socket sock;
	   @SuppressWarnings("unused")
	private BufferedInputStream reader = null;
	   
	   ObjectOutputStream objectOutputStream;
	   ObjectInputStream objectInputStream;
	   
	   ConnexionDB connexionDB ;
	   UtilisateurDB utilisateurDB;
	   LoginDB loginDB;
	   
	   BufferedReader in;
	   PrintWriter out;
	   
	   public ProcessusAuthentification(Socket pSock,ConnexionDB connexionDB){

           utilisateurDB = new UtilisateurDB(connexionDB);
           loginDB = new LoginDB(connexionDB);
           
           sock = pSock;
           this.connexionDB = connexionDB;
	   }
	   
	   Utilisateur utilisateur;
	   
	   //Le traitement lancé dans un thread séparé
	   public void run() {
	      System.out.println("Serveur: Authentification en cours");
	      
	      boolean closeConnexion = false;
	      //tant que la connexion est active, on traite les demandes
	      while(!sock.isClosed()) {
	         
	         try {
	        	
               	// faire un while, tant que la connexion reussie pas
               	
               	in = new BufferedReader (new InputStreamReader (sock.getInputStream()));
               	
	        	String requeteType = in.readLine();
	        	System.out.println(requeteType);
	        	
	        	String nomutilisateur = requeteType.split(",")[0];
	        	String password = requeteType.split(",")[1];
	        	 
	        	 reader = new BufferedInputStream(sock.getInputStream());
	        	 
	        	 utilisateur = loginDB.getUtilisateurByLoginMotdepasse(nomutilisateur, password);
         	   	
	        	 objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
	        	 
	             if (utilisateur!=null)
	             {
	     	   		 ActionServeur.addUser(new SSocket(utilisateur, sock));

	     	   		 System.out.println("Utilisateurs connectés: "+ActionServeur.getUsers().size());

	            	 closeConnexion = true;	
	            	 
	            	 objectOutputStream.writeObject(utilisateur);
	            	 objectOutputStream.writeObject(new GroupeDB(connexionDB).getAllGroupes());
	            	 
		             objectOutputStream.flush();
		             
	            	 new Thread(new DifferentsMessages(sock, connexionDB,utilisateur)).start();
	            	 
 		             break;
	             }
	             else {
	            	 
	            	 closeConnexion = false;	
	            	 Utilisateur utilisateur = null;
	                 objectOutputStream.writeObject(utilisateur);
	               	
		             objectOutputStream.flush();
	             	}
	             
	             if(closeConnexion){
	                System.err.println("THREAD FERMEE !");
	          
	                objectOutputStream = null;
	                reader = null;
	                sock.close();
	                break;
	             }
	         }
	         catch(SocketException e){
	            System.err.println("Une CONNEXION A ETE INTERROMPUE ! 1 ");	            
	            
	            ActionServeur.removeUser(new SSocket(utilisateur, sock));
	            
	            System.err.println("Un utilisateur s'est deconnecté ");
	            
	            break;
	         } catch (IOException e) {
	            e.printStackTrace();
	         } 
	   }
   }
}

