package Model;

import java.io.Serializable;

public class UtilisateurGroupe implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2445362452841943666L;
	private Groupe groupe;
	private Utilisateur utilisateur;
	
	public UtilisateurGroupe(Groupe groupe,Utilisateur utilisateur) {
		this.setGroupe(groupe);
		this.setUtilisateur(utilisateur);
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupe == null) ? 0 : groupe.hashCode());
		result = prime * result + ((utilisateur == null) ? 0 : utilisateur.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		UtilisateurGroupe ug = (UtilisateurGroupe)obj;
		
		if (this.groupe.getId() == ug.groupe.getId())
			if (this.utilisateur.getId() == ug.utilisateur.getId())
				return true;
		
		return false;
	}
	

	
	
	
}
