package model;

import java.util.ArrayList;

import Model.Groupe;
import Model.Utilisateur;

public class ActionUtilisateur {
	
	static private Utilisateur loggedUser;
	static private ArrayList<Groupe> groupes = new ArrayList<>();
	
	static Boolean isLogged = false;
	
	public static Utilisateur getUtilisateur() {
		return loggedUser;
	}
	
	public static void setUtilisateur(Utilisateur utilisateur) {
		isLogged = true;
		loggedUser = utilisateur;
	}

	public static void setGroupes(ArrayList<Groupe> group) {
		groupes = group;
	}
	

	public static ArrayList<Groupe> getGroupes() {
		return groupes;
	}
	
	public static boolean isLogged() {
		return isLogged;
	}
	
	public static void isLogout() {
		isLogged = false;
		loggedUser = null;
	}
}

