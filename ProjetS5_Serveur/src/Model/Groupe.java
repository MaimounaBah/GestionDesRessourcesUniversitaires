package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Groupe implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8447497309139966543L;
	private int id;
	private String nom;
	
	//private ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
	private ArrayList<Ticket> tickets = new ArrayList<>();
	
	public Groupe(int id, String nom) {
		this(nom);
		this.id = id;
	}

	public Groupe(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return this.getNom();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		Groupe other = (Groupe) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
}
