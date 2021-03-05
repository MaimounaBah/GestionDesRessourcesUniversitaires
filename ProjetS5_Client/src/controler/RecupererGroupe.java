package controler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Model.Groupe;
import model.ActionUtilisateur;
import view.Client;

public class RecupererGroupe implements Runnable {

	private Socket socket;
	public Thread thread;

	private PrintWriter out = null;
	
	ObjectInputStream objectInputStream;

	private ArrayList<Groupe> groupes = new ArrayList<>();
	Client client;
	
	public RecupererGroupe(Socket socket,Client client) {
		 this.socket = socket;
		 this.client = client;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		System.out.println("Client: Renvoie moi les groupes");

		try {
			out = new PrintWriter(socket.getOutputStream());
			
			out.println(ActionUtilisateur.getUtilisateur().getId()+"-"+"groupes");
			out.flush();

			objectInputStream = new ObjectInputStream(socket.getInputStream());
			groupes = (ArrayList<Groupe>) objectInputStream.readObject();
			
			if (groupes != null) {
				System.out.println("Reponse: "+groupes.size());
			}
			else {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
