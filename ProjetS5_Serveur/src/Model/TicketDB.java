package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TicketDB implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4803518894050479514L;
	private ArrayList<Ticket> tickets = null;
	ConnexionDB connexionDB ;
	PreparedStatement prepare;
	
	GroupeDB groupeDB;
	UtilisateurDB utilisateurDB;
	MessageDB messageDB;
	
	public TicketDB(ConnexionDB connexionDB,GroupeDB groupeDB){
		this.connexionDB = connexionDB;
		
		utilisateurDB = new UtilisateurDB(connexionDB);
		messageDB = new MessageDB(connexionDB,this);
		
		if (groupeDB == null)
			groupeDB = new GroupeDB(connexionDB);
		else
			this.groupeDB = groupeDB;
	}
	
	public Ticket save(Ticket ticket,Utilisateur utilisateur,Groupe groupe)
	{
		Ticket t = null;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("insert into ticket(titreFil,date_envoie,idU,idGroupe) values(?,?,?,?);"); 
			 prepare.setString(1, ticket.getTitle());
			 
			 Object param = new java.sql.Timestamp(ticket.getDateCreation().getTime());
			 
			 prepare.setObject(2,param);
			 
			 prepare.setInt(3, utilisateur.getId());
			 prepare.setInt(4, groupe.getId());
			 
			 prepare.execute();		
			 ticket.setId(getLastInserred().getId());
			 
			 for (Message message: ticket.getMessages())
			 {
				 new MessageDB(connexionDB, this).save(message);
			 }
			 
			 t = ticket;
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
		
		return t;
	}
	
	public ArrayList<Ticket> getAllTickets() {
		tickets = new ArrayList<Ticket>();
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM ticket;");
		
			while (rsult.next())
			{				
				
				Timestamp timestamp = rsult.getTimestamp(3);	 
				Ticket t = new Ticket(rsult.getInt(1), rsult.getString(2), new java.util.Date(timestamp.getTime()),utilisateurDB.getUtilisateurById(rsult.getInt(4)),groupeDB.getGroupeById(rsult.getInt(5)));
				
				t.setMessages(messageDB.getMessagesByTicket(t));
				tickets.add(t);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tickets;
	}
	
	public Ticket getLastInserred()
	{
		Ticket ticket = null;
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM ticket order by idFil desc limit 1;");
		
			if (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);	   
				
				ticket = new Ticket(rsult.getInt(1), rsult.getString(2),new java.util.Date(timestamp.getTime()),utilisateurDB.getUtilisateurById(rsult.getInt(4)),groupeDB.getGroupeById(rsult.getInt(5)));								
				ticket.setMessages(messageDB.getMessagesByTicket(ticket));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return ticket;
	}
	
	public void deleteLine(int id)
	{
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("delete from ticket where idFil = ?;"); 
			 prepare.setString(1, String.valueOf(id));
			 prepare.execute();		
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
	
	public Ticket getTicketById(int id) {
		Ticket ticket = null;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM ticket where idFil ="+ id +" ;");
		
			while (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);	  
				
				ticket = new Ticket(rsult.getInt("idFil"), rsult.getString("TitreFil"),new java.util.Date(timestamp.getTime()),utilisateurDB.getUtilisateurById(rsult.getInt("idU")),groupeDB.getGroupeById(rsult.getInt("idGroupe")));								
				ticket.setMessages(messageDB.getMessagesByTicket(ticket));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return ticket;
	}
	
	public ArrayList<Ticket> getTicketsByGroupe(Groupe groupe) {
		
		ArrayList<Ticket> tickets = new ArrayList<>();
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM ticket where idGroupe ="+ groupe.getId() +" ;");
		
			while (rsult.next())
			{
				Timestamp timestamp = rsult.getTimestamp(3);	   
				
				Ticket ticket = new Ticket(rsult.getInt(1), rsult.getString(2),new java.util.Date(timestamp.getTime()),
							utilisateurDB.getUtilisateurById(rsult.getInt(4)),
							groupe);
				
				ticket.setMessages(messageDB.getMessagesByTicket(ticket));
				
				tickets.add(ticket);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return tickets;
	}
}
