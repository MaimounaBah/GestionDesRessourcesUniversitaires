package Model;

import java.util.ArrayList;

public class ActionServeur {
	
	private static ArrayList<SSocket> utilisateurConnectes = new ArrayList<>();
	
	public static ArrayList<SSocket> addUser(SSocket ss) {
		utilisateurConnectes.add(ss);
		
		return utilisateurConnectes;
	}
	
	public static ArrayList<SSocket> removeUser(SSocket ss) {

		if (utilisateurConnectes.contains(ss))
			utilisateurConnectes.remove(ss);
		
		return utilisateurConnectes;
	}
	
	public static ArrayList<SSocket> getUsers() {
		return utilisateurConnectes;
	}
}
