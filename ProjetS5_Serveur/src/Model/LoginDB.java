package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginDB implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -611148642005061843L;
	ConnexionDB connexionDB;
	PreparedStatement prepare;
	static boolean isLogged = false;
	
	static Utilisateur loggedUser;
	
	UtilisateurGroupeDB utilisateurGroupeDB;
	
	public LoginDB(ConnexionDB connexionDB){
		this.connexionDB = connexionDB;
		
		utilisateurGroupeDB = new UtilisateurGroupeDB(connexionDB);
		
	}
	
	public Utilisateur getUtilisateurByLoginMotdepasse(String login, String motdepasse) {
		Utilisateur utilisateur = null;
		
		try
		{
			//conn = connexionDB.connect("projets5_grunivertaires", "root","");
			Statement state = connexionDB.conn.createStatement();

			ResultSet rsult = state.executeQuery("SELECT * FROM utilisateur where usernameU = '"+ login +"' and motDePasse = '"+motdepasse+"' ;");
		
			if (rsult.next())
			{
				ArrayList<String> roles = new ArrayList<>();
								
				for (String s : rsult.getObject(5).toString().split(","))
					roles.add(s);	
				
				utilisateur = new Utilisateur(Integer.valueOf(rsult.getObject(1).toString()), rsult.getObject(2).toString()
						, rsult.getObject(3).toString(), rsult.getObject(4).toString()
						, rsult.getObject(5).toString(), 
						roles);
				
				ArrayList<Groupe> groupes = new ArrayList<>();
				
				for (UtilisateurGroupe ug: utilisateurGroupeDB.getAllUtilisateursGroupe())
					if (ug.getUtilisateur().getId() == utilisateur.getId())	
						groupes.add(ug.getGroupe());
				
					for (Message m: new MessageDB(connexionDB, new TicketDB(connexionDB,new GroupeDB(connexionDB))).getUserMessages(utilisateur))
					{
						if (!groupes.contains(m.getTicket().getGroupe()))
						{
							groupes.add(m.getTicket().getGroupe());
						}
					}
				
				utilisateur.setGroupes(groupes);
				
				loggedUser = utilisateur;
				isLogged = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		return utilisateur;
	}

	public static Utilisateur getUtilisateur() {
		
	
		return loggedUser;
	}
	
	public static boolean isLogged() {
		return isLogged;
	}
	
	public static void isLogout() {
		isLogged = false;
	}
}
