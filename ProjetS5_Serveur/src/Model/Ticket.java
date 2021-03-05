package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Ticket implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1093117129898368140L;
	private int id;
	private String title;
	private Date dateCreation;
	private Utilisateur createurTicket;
	private Groupe groupe;
	
	private ArrayList<Message> messages = new ArrayList<>(); 
	
	public Ticket(int id,String title, Date dateCreation,Utilisateur createurTicket,Groupe group) {
		this(title,dateCreation,createurTicket,group);
		
		this.id = id;
	}
	
	public Ticket(String title, Date dateCreation,Utilisateur createurTicket,Groupe group) {
		this.title = title;
		this.dateCreation = dateCreation;
		this.createurTicket = createurTicket;
		this.groupe = group;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Utilisateur getCreateurTicket() {
		return createurTicket;
	}


	public void setCreateurTicket(Utilisateur createurTicket) {
		this.createurTicket = createurTicket;
	}


	public Groupe getGroupe() {
		return groupe;
	}


	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
}
