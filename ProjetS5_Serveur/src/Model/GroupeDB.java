package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupeDB implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1841290675228662551L;
	private ArrayList<Groupe> groupes = null;
	ConnexionDB connexionDB ;
	PreparedStatement prepare;

	TicketDB ticketDB;
	public GroupeDB(ConnexionDB connexionDB){
		this.connexionDB = connexionDB;
		
		ticketDB = new TicketDB(connexionDB,this);
	}
	
	public void save(Groupe groupe)
	{
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("insert into groupe(nomGroupe) values(?);"); 
			 prepare.setString(1, groupe.getNom());
			 
			 prepare.execute();		
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
	
	public ArrayList<Groupe> getAllGroupes() {
		groupes = new ArrayList<Groupe>();
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state =connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM groupe;");
		
			while (rsult.next())
			{
				
				Groupe u = new Groupe(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString());
				u.setTickets(ticketDB.getTicketsByGroupe(u));
				
				groupes.add(u);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return groupes;
	}
	
	public Groupe getLastInserred()
	{
		Groupe groupe = null;
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM groupe order by idGroupe desc limit 1;");
		
			if (rsult.next())
			{
				groupe = new Groupe(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString());
				groupe.setTickets(ticketDB.getTicketsByGroupe(groupe));				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return groupe;
	}
	
	public void deleteLine(int id)
	{
		try{
			 prepare = connexionDB.conn.prepareStatement("delete from groupe where idGroupe = ?;"); 
			 prepare.setString(1, String.valueOf(id));
			 prepare.execute();		
		}
		catch(Exception e){
			 e.printStackTrace();    
		}
	}
	
	public Groupe getGroupeById(int id) {
		Groupe groupe = null;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state =connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM groupe where idGroupe ="+ id +" ;");
		
			if (rsult.next())
			{
				groupe = new Groupe(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString());
			}
			
			groupe.setTickets(ticketDB.getTicketsByGroupe(groupe));	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return groupe;
	}

	public void updateGroupeById(int id,Groupe groupe) {
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("update groupe set nomGroupe = ? where idGroupe = ?;"); 
			 prepare.setString(1, groupe.getNom());
			 prepare.setInt(2, id);			 
			 prepare.execute();	
			 prepare.close();
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
	

	public boolean isGroupeExiste(String nom)
	{
		boolean bool = false;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM groupe where nomGroupe = '"+nom+"';");
			
			if (rsult.next())
				bool = true;
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
		
		return bool;
	}
}
