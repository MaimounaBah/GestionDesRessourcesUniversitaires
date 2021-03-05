package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2340664385627528911L;
	private int id;
	private String nom,prenom,motdepasse,username;
	
	private ArrayList<String> roles = new ArrayList<>();
	
	private ArrayList<Ticket> mesTickets = new ArrayList<>();
	private ArrayList<Message> messagesEnvoyes  = new ArrayList<>();
	
	private ArrayList<Groupe> groupes = new ArrayList<>();
	
	public Utilisateur(String nom, String prenom, String username, String motdepasse,ArrayList<String> roles) {
		
		this(nom,prenom,username);
		this.motdepasse = motdepasse;
		this.roles = roles;	
	}

	
	public Utilisateur(int id, String nom, String prenom, String username,String motdepasse, ArrayList<String> roles) {
		this(nom,prenom,username,motdepasse,roles);
		
		this.id = id;
	}


	public Utilisateur(String nom, String prenom, String username) {
		this.nom = nom;
		this.prenom = prenom;
		this.username = username;
	}


	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void addRole(String role)
	{
		if (!this.roles.contains(role))
			this.roles.add(role);
	}
	
	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Ticket> getMesTickets() {
		return mesTickets;
	}

	public void setMesTickets(ArrayList<Ticket> mesTickets) {
		this.mesTickets = mesTickets;
	}

	public ArrayList<Message> getMessagesEnvoyes() {
		return messagesEnvoyes;
	}

	public void setMessagesEnvoyes(ArrayList<Message> messagesEnvoyes) {
		this.messagesEnvoyes = messagesEnvoyes;
	}


	public ArrayList<Groupe> getGroupes() {
		return groupes;
	}


	public void setGroupes(ArrayList<Groupe> groupes) {
		this.groupes = groupes;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
