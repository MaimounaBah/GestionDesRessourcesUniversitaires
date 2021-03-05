package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import Model.ConnexionDB;
import Model.GroupeDB;
import Model.ActionServeur;
import Model.LoginDB;
import Model.Message;
import Model.MessageDB;
import Model.SSocket;
import Model.Ticket;
import Model.TicketDB;
import Model.Utilisateur;
import Model.UtilisateurDB;

public class DifferentsMessages implements Runnable {
	  private Socket sock;
	   ObjectInputStream objectInputStream;
	   ObjectOutputStream objectOutputStream;
	   
	   BufferedReader in;
	   ConnexionDB connexionDB;
	   UtilisateurDB utilisateurDB;
	   LoginDB loginDB;
	   Message message = null;
	   Utilisateur utilisateur;
	   
	   public DifferentsMessages(Socket pSock,ConnexionDB connexionDB,Utilisateur utilisateur){

		   utilisateurDB = new UtilisateurDB(connexionDB);
           loginDB = new LoginDB(connexionDB);
           this.utilisateur = utilisateur;
           
           sock = pSock;
           this.connexionDB = connexionDB;
	   }
	   
	   public void run() {
	      
	      boolean closeConnexion = false;

	      while(!sock.isClosed()) {
	         
	         try {
	        	 System.out.println("Attente d'un message");
	 			
	        	 objectInputStream = new ObjectInputStream(sock.getInputStream());
               	
	        	 Object object = objectInputStream.readObject();
	        	
	        	 Ticket ticket = null;
	        	 
	        	 if (object instanceof Message)
	        		 message = (Message) object;
	        	 else
		        	 if (object instanceof Ticket)
		        		 ticket = (Ticket) object;
	        	 
	        	 if (message != null)
	        	 {
	        		 message =  new MessageDB(connexionDB, new TicketDB(connexionDB, new GroupeDB(connexionDB))).save(message);
	        	 
	        		 for (SSocket s : ActionServeur.getUsers())
        			 {
        				 if (!s.getSocket().isClosed() && s.getUtilisateur() != null) 
        				 {
		    	        	 objectOutputStream = new ObjectOutputStream(s.getSocket().getOutputStream());
		    	        	 objectOutputStream.writeObject(message);
		    	        	 
		    	        	 objectOutputStream.flush();
        				 }
        			 }
	        	 }
        		 else 
        		 {
        			 ticket = new TicketDB(connexionDB, new GroupeDB(connexionDB)).save(ticket, ticket.getCreateurTicket(), ticket.getGroupe());
    			 	 
        			 for (SSocket s : ActionServeur.getUsers())
        			 {
        				 if (!s.getSocket().isClosed() && s.getUtilisateur() != null) 
        				 {
		    	        	 objectOutputStream = new ObjectOutputStream(s.getSocket().getOutputStream());
		    	        	 objectOutputStream.writeObject(ticket);
		    	        	 
		    	        	 objectOutputStream.flush();
        				 }
        			 }
        		 }
	        	 
	        	 if(closeConnexion){
                	System.err.println("THREAD FERMEE ! ");
   	          
                	objectInputStream = null;
                	objectOutputStream = null;
   	                sock.close();
   	                break;
             	}
	         }
	         catch(SocketException e){
	            System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
	          
	            System.err.println(utilisateur.getPrenom()+" "+utilisateur.getNom()+" s'est deconnecté ");
	            
	            ActionServeur.removeUser(new SSocket(utilisateur, sock));
	            
	            break;
	         } catch (IOException e) {
	            e.printStackTrace();
	         } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	   }
   }
}