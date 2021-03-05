package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UtilisateurGroupeDB implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 265937254737963956L;
	ConnexionDB connexionDB ;
	PreparedStatement prepare;

	
	GroupeDB groupeDB;
	UtilisateurDB utilisateurDB ;
	
	public UtilisateurGroupeDB(ConnexionDB connexionDB){
		this.connexionDB = connexionDB;
		
		utilisateurDB = new UtilisateurDB(connexionDB);
		groupeDB = new GroupeDB(connexionDB);
	}
	
	public boolean addUsersToGroupes(ArrayList<Utilisateur> listeUtilisateurs,ArrayList<Groupe> listeGroupes) {
		
		ArrayList<UtilisateurGroupe> utilisateursGroupes = new ArrayList<>();
		
		// pour chaque groupe
		for (Groupe groupe : listeGroupes)
		{
			// pour chaque utilisateur
			for (Utilisateur utilisateur : listeUtilisateurs)
			{
				UtilisateurGroupe ug1 = new UtilisateurGroupe(groupe,utilisateur);
				
				if (!getAllUtilisateursGroupe().contains(ug1))
					utilisateursGroupes.add(ug1);
			}
		}
		
		for(UtilisateurGroupe ug: utilisateursGroupes)
		{
			try
			{
				//conn = connexionDB.connect("projets5_grunivertaires", "root","");
				 prepare = connexionDB.conn.prepareStatement("insert into groupeUtilisateur(idU,idGroupe) values(?,?);"); 
				 prepare.setInt(1, ug.getUtilisateur().getId());
				 prepare.setInt(2, ug.getGroupe().getId());
				 prepare.execute();		
			}
			catch(Exception e)
			{
				 e.printStackTrace();    
			}
		}
		
		return false;
	}
	
	public boolean removeUsersToGroupes(ArrayList<Utilisateur> listeUtilisateurs,ArrayList<Groupe> listeGroupes) {
		
		ArrayList<UtilisateurGroupe> utilisateursGroupes = new ArrayList<>();
		
		// pour chaque groupe
		for (Groupe groupe : listeGroupes)
		{
			// pour chaque utilisateur
			for (Utilisateur utilisateur : listeUtilisateurs)
			{
				UtilisateurGroupe ug1 = new UtilisateurGroupe(groupe,utilisateur);
				
				if (getAllUtilisateursGroupe().contains(ug1))
					utilisateursGroupes.add(ug1);
			}
		}
		
		System.out.println(utilisateursGroupes.size());
		
		for(UtilisateurGroupe ug: utilisateursGroupes)
		{
			try
			{
//conn = connexionDB.connect("projets5_grunivertaires", "root","");
				 prepare = connexionDB.conn.prepareStatement("delete from groupeutilisateur where idU = ? and idGroupe = ?;"); 
				 prepare.setInt(1, ug.getUtilisateur().getId());
				 prepare.setInt(2, ug.getGroupe().getId());
				 
				 prepare.execute();		
			}
			catch(Exception e)
			{
				 e.printStackTrace();    
			}
		}
		return false;
	}
	

	public ArrayList<UtilisateurGroupe> getAllUtilisateursGroupe() {
		
		ArrayList<UtilisateurGroupe> utilisateursgroupes = new ArrayList<UtilisateurGroupe>();
		
		try
		{
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM groupeutilisateur;");

			ArrayList<Groupe> groupes = new ArrayList<>();

			while (rsult.next())
			{
				Utilisateur u = utilisateurDB.getUtilisateurById(rsult.getInt("idU"));
				
				Groupe g = groupeDB.getGroupeById(rsult.getInt("idGroupe"));
				
				groupes.add(g);
				u.setGroupes(groupes);
				
				UtilisateurGroupe ug = new UtilisateurGroupe(g,u);
				
				utilisateursgroupes.add(ug);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return utilisateursgroupes;
	}

	public ArrayList<Groupe> getGroupesForUser(int id) {
		
		Utilisateur u = utilisateurDB.getUtilisateurById(id);
		
		ArrayList<UtilisateurGroupe> utilisateursgroupes = new ArrayList<UtilisateurGroupe>();
		ArrayList<Groupe> groupes = new ArrayList<>();
		try
		{
			Statement state = connexionDB.conn.createStatement();
			ResultSet rsult = state.executeQuery("SELECT * FROM groupeutilisateur where idU = "+u.getId()+";");

		

			while (rsult.next())
			{
				Groupe g = groupeDB.getGroupeById(Integer.valueOf(rsult.getObject(2).toString()));
				groupes.add(g);
				u.setGroupes(groupes);
				
				UtilisateurGroupe ug = new UtilisateurGroupe(g,u);
				
				utilisateursgroupes.add(ug);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return groupes;
	}

	
}

