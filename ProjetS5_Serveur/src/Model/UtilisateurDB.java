package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class UtilisateurDB implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4136364682287014146L;
	private ArrayList<Utilisateur> utilisateurs = null;
	ConnexionDB connexionDB;
	Connection con ;
	PreparedStatement prepare;
	public UtilisateurDB(ConnexionDB connexionDB){
		this.connexionDB = connexionDB;
		
	}
	
	public void save(Utilisateur utilisateur)
	{
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("insert into utilisateur(nomU,prenomU,usernameU,motDePasse,roles) values(?,?,?,?,?);"); 
			 prepare.setString(1, utilisateur.getNom());
			 prepare.setString(2, utilisateur.getPrenom());
			 prepare.setString(3, utilisateur.getUsername());
			 prepare.setString(4, utilisateur.getMotdepasse());
			 
			 String roles = "";
			 
			 Iterator<String> iter = utilisateur.getRoles().iterator();
			 
			 while(iter.hasNext())
			 {
				 roles+=iter.next()+",";
			 }
			 
			 prepare.setString(5, roles);
			 
			 prepare.execute();		
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
	
	public ArrayList<Utilisateur> getAllUtilisateurs() {
		utilisateurs = new ArrayList<Utilisateur>();
		try
		{
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM utilisateur;");
		
			while (rsult.next())
			{
				ArrayList<String> roles = new ArrayList<>();
				
				for (String s : rsult.getObject(5).toString().split(","))
					roles.add(s);
				
				Utilisateur u = new Utilisateur(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString()
						, rsult.getObject(3).toString(), rsult.getObject(4).toString()
						, rsult.getObject(5).toString(), 
						roles);
				
				utilisateurs.add(u);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return utilisateurs;
	}
	
	public Utilisateur getLastInserred()
	{
		Utilisateur utilisateur = null;
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM utilisateur order by idU desc limit 1;");
		
			while (rsult.next())
			{
				ArrayList<String> roles = new ArrayList<>();
				
				for (String s : rsult.getObject(5).toString().split(","))
					roles.add(s);
				
				
				utilisateur = new Utilisateur(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString()
						, rsult.getObject(3).toString(), rsult.getObject(4).toString()
						, rsult.getObject(5).toString(), 
						roles);
								
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return utilisateur;
	}
	
	public void deleteLine(int id)
	{
		try
		{
			 prepare = connexionDB.conn.prepareStatement("delete from utilisateur where idU = ?;"); 
			 prepare.setString(1, String.valueOf(id));
			 prepare.execute();		
			 
			 prepare =connexionDB. conn.prepareStatement("delete from groupeutilisateur where idU = ?;"); 
			 prepare.setString(1, String.valueOf(id));
			 prepare.execute();		
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
	

	public boolean isUserExiste(String username)
	{
		boolean bool = false;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM utilisateur where usernameU = '"+username+"';");
			
			if (rsult.next())
				bool = true;
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
		
		return bool;
	}
	
	
	public Utilisateur getUtilisateurById(int id) {
		Utilisateur utilisateur = null;
		
		try
		{
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM utilisateur where idU ="+ id +" ;");
		
			while (rsult.next())
			{
				ArrayList<String> roles = new ArrayList<>();			
				for (String s : rsult.getObject("roles").toString().split(","))
					roles.add(s);
				
				utilisateur = new Utilisateur(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString()
						, rsult.getObject(3).toString(), rsult.getObject(4).toString()
						, rsult.getObject(5).toString(), 
						roles);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		return utilisateur;
	}

	public void updateUtilisateurById(int id,Utilisateur utilisateur) {
		try
		{
			// conn = connexionDB.connect("projets5_grunivertaires", "root","");
			 prepare = connexionDB.conn.prepareStatement("update utilisateur set nomU = ? where idU = ?;"); 
			 prepare.setString(1, utilisateur.getNom());
			 prepare.setInt(2, id);			 
			 prepare.execute();	
			 prepare.close();
			 prepare = connexionDB.conn.prepareStatement("update utilisateur set prenomU = ? where idU = ?;"); 
			 prepare.setString(1,utilisateur.getPrenom());
			 prepare.setInt(2, id);	
			 prepare.execute();	
			 prepare.close();
			 prepare = connexionDB.conn.prepareStatement("update utilisateur set usernameU = ? where idU = ?;"); 
			 prepare.setString(1,utilisateur.getPrenom());
			 prepare.setInt(2, id);	
			 prepare.execute();		
			 prepare.close();
			 
			 prepare = connexionDB.conn.prepareStatement("update utilisateur set usernameU = ? where idU = ?;"); 
			 prepare.setString(1,utilisateur.getUsername());
			 prepare.setInt(2, id);	
			 prepare.execute();	
			 prepare.close();
		}
		catch(Exception e)
		{
			 e.printStackTrace();    
		}
	}
}