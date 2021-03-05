package controler;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Groupe;
import Model.Utilisateur;
import model.ActionUtilisateur;
import view.Client;
import view.Login;

public class AuthentificationDuClient implements Runnable  {

	private Socket socket;
	public Thread t2;

	private PrintWriter out = null;
	
	ObjectInputStream objectInputStream;
	private String login, password;
	public boolean authentifier = false;
	private Login loginF;
	
	public AuthentificationDuClient(Socket s,String login, String password,Login loginF) {
		 this.socket = s;
		 this.login = login;
		 this.password = password;
		 this.loginF = loginF;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {

		try {
			out = new PrintWriter(socket.getOutputStream());
			out.println(login+","+password);
			out.flush();
			System.out.println("Client: Tentative de connexion: " + login+" - "+password);
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			Utilisateur utilisateur = (Utilisateur)objectInputStream.readObject();
			
			if (utilisateur != null) {
				
				System.out.println("Client: Connecté");
				
				ActionUtilisateur.setUtilisateur(utilisateur);
				ActionUtilisateur.setGroupes((ArrayList<Groupe>)objectInputStream.readObject());

				loginF.setVisible(false);
				
				JOptionPane.showMessageDialog(null, "Connecté avec succès !", "Succès !", JOptionPane.INFORMATION_MESSAGE);
			
				Client client = new Client(this.socket);
				client.setVisible(true);
			}
			else 
			{
				System.err.println("Client: Non connecté");
				
				loginF.labelError.setVisible(true);
				loginF.boutonSubmit.setEnabled(true);
				loginF.labelError.setForeground(Color.RED);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}