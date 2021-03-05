package Model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageDB implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9108971313181923892L;

	private ArrayList<Message> messages = null;
	
	ConnexionDB connexionDB ;
	PreparedStatement prepare;

	TicketDB ticketDB;
	UtilisateurDB utilisateurDB;
	
	public MessageDB(ConnexionDB connexionDB,TicketDB ticketDB) {
		this.connexionDB = connexionDB;
		
		utilisateurDB = new UtilisateurDB(connexionDB);
		
		if (ticketDB == null)
			ticketDB = new TicketDB(connexionDB,null);
		else
			this.ticketDB = ticketDB;
	}
	
	public Message save(Message message)
	{
		Message m = null;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("insert into message(contenuMsg,date_envoie,idFil,idU) values(?,?,?,?);"); 
			 prepare.setString(1, message.getTexte());
			 
			 System.out.println();
			 
			 Object param = new java.sql.Timestamp(message.getDateEnvoie().getTime());
			 
			 prepare.setObject(2,param);

			 prepare.setInt(3, message.getTicket().getId());
			 prepare.setInt(4,  message.getExpediteur().getId());
			
			 prepare.execute();		
			 
		 	message.setId(getLastInserred().getId());
			 
			 m  = message;
			 
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
		
		return m;
	}
	
	public ArrayList<Message> getAllMessages() {
		messages = new ArrayList<Message>();
		try
		{
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM message;");
		
			while (rsult.next())
			{			
				Timestamp timestamp = rsult.getTimestamp(3);	
				
				Message message = new Message(rsult.getInt(1),  rsult.getString(2), new java.util.Date(timestamp.getTime()), ticketDB.getTicketById(rsult.getInt(4)), 
						utilisateurDB.getUtilisateurById(rsult.getInt(5)));
			
				messages.add(message);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return messages;
	}
	
	public Message getLastInserred()
	{
		Message message = null;
		try
		{
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM message order by idMsg desc limit 1;");
		
			if (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);	
				
				message = new Message(rsult.getInt("idMsg"),  rsult.getString("contenuMsg"), new java.util.Date(timestamp.getTime()), ticketDB.getTicketById(rsult.getInt("idFil")), 
						utilisateurDB.getUtilisateurById(rsult.getInt("idU")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return message;
	}
	
	public void deleteLine(int id)
	{
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("delete from message where idMsg = ?;"); 
			 prepare.setString(1, String.valueOf(id));
			 prepare.execute();		
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
	
	public Message getMessageById(int id) {
		Message message = null;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM message where idMsg ="+ id +" ;");
		
			while (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);	
				
				message = new Message(rsult.getInt(1),  rsult.getString(2),new java.util.Date(timestamp.getTime()), ticketDB.getTicketById(rsult.getInt(4)), 
						utilisateurDB.getUtilisateurById(rsult.getInt(5)));
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return message;
	}
	
	public ArrayList<Message> getMessagesByTicket(Ticket ticket) {
		
		ArrayList<Message> messages = new ArrayList<>();
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM message where idFil ="+ ticket.getId() +" ;");
		
			while (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);
				
				Message message = new Message(rsult.getInt(1),  rsult.getString(2), new java.util.Date(timestamp.getTime()), ticket, 
						utilisateurDB.getUtilisateurById(rsult.getInt(5)));
				
				messages.add(message);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return messages;
	}
	
	
	public ArrayList<Message> getUserMessages(Utilisateur utilisateur) {
		
		ArrayList<Message> messages = new ArrayList<>();
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			
			ResultSet rsult = state.executeQuery("SELECT * FROM message where idU ="+ utilisateur.getId() +" ;");
		
			while (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);
				
				Message message = new Message(rsult.getInt("idMsg"),  rsult.getString("contenuMsg"), new java.util.Date(timestamp.getTime()), ticketDB.getTicketById(rsult.getInt("idFil")), 
						utilisateur);
				
				messages.add(message);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return messages;
	}
}
