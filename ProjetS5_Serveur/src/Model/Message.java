package Model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6506405534018869401L;
	private int id;
	private String texte;
	private Date dateEnvoie;
	private Ticket ticket;
	private Utilisateur expediteur;


	public Message(String texte, Date dateEnvoie,Ticket ticket,Utilisateur expediteur) 
	{
		this.texte = texte;
		this.dateEnvoie = dateEnvoie;
		
		this.ticket = ticket;
		this.expediteur = expediteur;
	}
	
	public Message(int id, String texte, Date dateEnvoie,Ticket ticket,Utilisateur expediteur) 
	{
		this(texte,dateEnvoie,ticket,expediteur);
		this.id = id;
	}
	
	public String getTexte() {
		return texte;
	}
	
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDateEnvoie() {
		return dateEnvoie;
	}
	
	public void setDateEnvoie(Date dateEnvoie) {
		this.dateEnvoie = dateEnvoie;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Utilisateur getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(Utilisateur expediteur) {
		this.expediteur = expediteur;
	}
}
