package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Model.Groupe;
import Model.Message;
import Model.Ticket;
import model.ActionUtilisateur;

public class AjoutTicket extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7202079428867692162L;
	JPanel panelTitre = new JPanel();
	JLabel labelTitre = new JLabel("Titre : ");	
	JTextField textAreaTitre = new JTextField();	
	
	JPanel panelMessage = new JPanel();
	JLabel labelMessage = new JLabel("Message (*)  : ");	
	JTextArea textAreaMessage = new JTextArea();	
	
	JPanel panelGroupe = new JPanel();
	JLabel labelGroupe = new JLabel("Groupe (*)  : ");
	JComboBox<Groupe> groupeSelect = new JComboBox<>();
	
	JPanel panelSubmit = new JPanel();	
	public JButton boutonSubmit = new JButton("Ajouter");	
	
	Socket socket;
	ArrayList<Groupe> groupes = new ArrayList<>();
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	
	Client client;
	
	public AjoutTicket(Socket socket, ArrayList<Groupe> groupes,Client client)
	{
		this.client = client;
		this.socket = socket;
		this.groupes = groupes;
		
		init();
		initPanel();
		initEvent();
	}
	
	public AjoutTicket()
	{		
		init();
		initPanel();
		initEvent();
	}
	
	
	public void init()
	{
		this.setTitle("Gestion ticket");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(500, 250);
		this.setLocationRelativeTo(null);
	}
	
	public void initPanel()
	{
		JPanel container = new JPanel();
		
			panelTitre.setLayout(new FlowLayout());
			panelTitre.add(labelTitre);
			textAreaTitre.setPreferredSize(new Dimension(370, 30));
			panelTitre.add(textAreaTitre);
			
			panelMessage.setLayout(new FlowLayout());
			panelMessage.add(labelMessage);
			textAreaMessage.setPreferredSize(new Dimension(370, 50));
			panelMessage.add(new JScrollPane(textAreaMessage));
			
			panelGroupe.setLayout(new FlowLayout());
			panelGroupe.add(labelGroupe);
			groupeSelect.setPreferredSize(new Dimension(380, 30));
			panelGroupe.add(groupeSelect);
			
			for (Groupe groupe: ActionUtilisateur.getGroupes()) {
				groupeSelect.addItem(groupe);
			}
			
			container.setLayout(new FlowLayout());
			
			container.add(panelTitre);
			container.add(panelGroupe);
			container.add(panelMessage);
			
			panelSubmit.add(boutonSubmit);
		
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(container,BorderLayout.CENTER);
		this.getContentPane().add(panelSubmit,BorderLayout.SOUTH);
	}
	
	public void initEvent() {
		
		boutonSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean ok = true;
				
				String titre = textAreaTitre.getText();
	
				String message = textAreaMessage.getText();
				
				Groupe groupe = (Groupe)groupeSelect.getSelectedItem();
				
				if (titre.isEmpty()) {
					ok  = false;
					textAreaTitre.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textAreaTitre.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				if (message.isEmpty()) {
					ok  = false;
					textAreaMessage.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textAreaMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				if (groupe == null)
					ok  = false;
				
				if (ok)
				{
					boutonSubmit.setEnabled(false);
					
					Ticket ticket = new Ticket(titre, new Date(),ActionUtilisateur.getUtilisateur(), groupe);
					ArrayList<Message> messages = new ArrayList<>();
					
					messages.add(new Message(message, new Date(), ticket, ActionUtilisateur.getUtilisateur()));
					
					ticket.setMessages(messages);
					
		     		try {
		    			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());		    			
						objectOutputStream.writeObject(ticket);
	            		objectOutputStream.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	
	public static void main(String[] args) {
		new AjoutTicket().setVisible(true);
	}
}



