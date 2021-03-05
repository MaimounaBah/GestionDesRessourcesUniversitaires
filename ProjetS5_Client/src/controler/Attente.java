package controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.Message;
import Model.Ticket;
import model.ActionUtilisateur;
import view.AjoutTicket;
import view.Client;

public class Attente implements Runnable {
	  private Socket sock;
	   ObjectInputStream objectInputStream;
	   ObjectOutputStream objectOutputStream;
	   
	   BufferedReader in;

	   Ticket ticket = null;
	   Client client;
	   AjoutTicket ajoutTicket;
	   
	   public Attente(Socket pSock,Client client,AjoutTicket ajoutTicket){
	      sock = pSock;
	      this.client = client;
	      this.ajoutTicket = ajoutTicket;
	   }
	   
	   public void run() {
		   
	      while(!sock.isClosed()) {
	    	  try 
	    	  {
				objectInputStream = new ObjectInputStream(sock.getInputStream());
				
				Object o = objectInputStream.readObject();
				
				Message message = null;
				
				if (o instanceof Message)
				{
					message = (Message)o;
						    			
        	  		client.updatePane(message);
				}
				else if (o instanceof Ticket) {
		
		    			ticket = (Ticket)o;
		    			
		    			System.out.println("Nouveau ticket "+ticket.getCreateurTicket().getId()+" - "+ActionUtilisateur.getUtilisateur().getId());
		    			
		    			if (ticket.getCreateurTicket().getId() == ActionUtilisateur.getUtilisateur().getId()) {
			    			if (client != null) {
			    				ajoutTicket.setVisible(false);
								
								client.updatePane(ticket);
				     		}
				     		else {
				     			ajoutTicket.boutonSubmit.setEnabled(true);
				     		}
		    			}
		    			else
		    			{
							client.updatePane(ticket);
		    				System.out.println("Nouveau message");
		    			}
					}
	    	  } 
	    	  catch (IOException e) 
	    	  {
				e.printStackTrace();
	    	  } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    	  }
				// TODO: handle exception
	    	  
	      }
   }
}

