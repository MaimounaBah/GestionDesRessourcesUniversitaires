package view;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import Model.Groupe;
import Model.Message;
import Model.QuitterAction;
import Model.Ticket;
import Model.Utilisateur;
import controler.Attente;
import model.ActionUtilisateur;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Client extends JFrame {

	private static final long serialVersionUID = 2059121263664013009L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenuItem exit = new JMenuItem(new QuitterAction("Quitter"));
	private JMenu edition = new JMenu("Edition");
	private JMenu apropos = new JMenu("Apropos");
	Utilisateur utilisateur = null;
	private JTree arbre;   
	private JPanel panelLeft;
	private JPanel panelCentre;
	private JPanel panelBottom;
	JPanel ecran = new JPanel();
	JTextArea champsMessage;
	JButton send,deconnexion,nouvelleConversation;
	
	JScrollPane scroll;
	
	Ticket ticketSelected = null;
	Socket socket;
	
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	
	AjoutTicket ajoutTicket = null;
	
	public Client(Socket socket)
	{
		this.socket = socket;
		
		if (ActionUtilisateur.isLogged())
			utilisateur = ActionUtilisateur.getUtilisateur();
		else
			utilisateur = new Utilisateur("Balde", "Mamadou Bhoye","aaa");
		
		if (utilisateur != null) {
			init();
			initMenu();
			initPanel();
			initEvent();
		}
		else {
				Client.this.setVisible(false);
				JOptionPane.showMessageDialog(null, "Une erreur s'est produite", "Erreur", JOptionPane.ERROR_MESSAGE);
			}

		ajoutTicket = new AjoutTicket(socket, utilisateur.getGroupes(),Client.this);
		
		Attente ff = new Attente(socket,Client.this,ajoutTicket);
		
		Thread aa = new Thread(ff);
		
		aa.start();
	}
	
	public void init()
	{
		this.setTitle("Client: "+utilisateur.getPrenom()+" - "+utilisateur.getNom());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1200, 500);
		this.setLocationRelativeTo(null);
	}
	
	public void initMenu()
	{
		fichier.add(exit);
		menuBar.add(fichier);
		menuBar.add(edition);	
		menuBar.add(apropos);
		
		this.setJMenuBar(menuBar);
	}
	
	public void initPanel()
	{
		panelLeft = new JPanel();
		panelCentre = new JPanel();	
		scroll = new JScrollPane(panelCentre);
		panelLeft.setLayout(new BorderLayout());
		
		buildTree(utilisateur.getGroupes());
		
		panelLeft.add(new JScrollPane(arbre));
		
		panelCentre.setLayout(new BorderLayout());
		
		panelCentre.setBackground(Color.WHITE);
		
		ecran.setLayout(new BoxLayout(ecran, BoxLayout.Y_AXIS));
		
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelCentre.add(ecran,BorderLayout.SOUTH);
	
		panelBottom = new JPanel();
		panelBottom.setPreferredSize(new Dimension(1020,100));
		panelBottom.setLayout(new BorderLayout());
		
		JPanel panelControl = new JPanel();
		panelControl.setLayout(new BorderLayout());
		panelBottom.add(panelControl,BorderLayout.LINE_START);
		
		JPanel panelMessageSend = new JPanel();
		panelBottom.add(panelMessageSend,BorderLayout.LINE_END);
		
		panelMessageSend.setLayout(new BorderLayout());
		panelMessageSend.setPreferredSize(new Dimension(1070,100));
		
		champsMessage = new JTextArea();
		champsMessage.setBorder(BorderFactory.createLineBorder(Color.black));
		send = new JButton(">>");
		send.setEnabled(false);
		champsMessage.setLineWrap(true);
		panelMessageSend.add(new JScrollPane(champsMessage),BorderLayout.CENTER);
		panelMessageSend.add(send,BorderLayout.EAST);
		
		nouvelleConversation = new JButton("+");
		nouvelleConversation.setPreferredSize(new Dimension(40, 40));
		
		deconnexion = new JButton("Se deconnecter");	

		panelControl.add(nouvelleConversation,BorderLayout.NORTH);
		panelControl.add(deconnexion,BorderLayout.SOUTH);
		
		this.getContentPane().add(panelLeft,BorderLayout.LINE_START);
		this.getContentPane().add(scroll,BorderLayout.CENTER);
		this.getContentPane().add(panelBottom,BorderLayout.SOUTH);
	}
	
	public void addMessage(boolean sender,Message message)
	{
		JPanel messageContainer = new JPanel();
		messageContainer.setLayout(new BorderLayout());
		messageContainer.setBackground(Color.WHITE);
		
			JPanel messagePanel = new JPanel();
			messagePanel.setLayout(new BorderLayout());
			
				JLabel nomEtPrenom = new JLabel(message.getExpediteur().getPrenom()+" "+message.getExpediteur().getNom());
				
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
				
				JLabel date = new JLabel(dateFormat.format(message.getDateEnvoie()));
				
				if (sender == false)
					nomEtPrenom.setHorizontalAlignment(SwingConstants.RIGHT);
				else
					date.setHorizontalAlignment(SwingConstants.RIGHT);
			
				JTextArea texte = new JTextArea();
				texte.setEditable(false);
				
				texte.setText(message.getTexte());
				
				texte.setBackground(Color.GREEN);
				texte.setOpaque(true);
				
			messagePanel.add(nomEtPrenom,BorderLayout.NORTH);
			messagePanel.add(texte,BorderLayout.CENTER);
			messagePanel.add(date,BorderLayout.SOUTH);
		
			if (sender == false)
				messageContainer.add(messagePanel,BorderLayout.LINE_END);
			else
				messageContainer.add(messagePanel,BorderLayout.LINE_START);
		
		ecran.add(messageContainer);
		
		scrollToBottom();
	}
	
	private void buildTree(ArrayList<Groupe> groupes) {
		
	    DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Liste des groupes");
	    
	    for (Groupe groupe : groupes) {
    	  if (groupe.getTickets().size() != 0) {
		      DefaultMutableTreeNode rep = new DefaultMutableTreeNode(groupe.getNom());  
		      
		      for (Ticket ticket :groupe.getTickets())
		      {
		    	  DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode(ticket.getId()+" - "+ticket.getTitle());
		    	  
		    	  rep.add(rep2);
		      }
		      
		      racine.add(rep);
    	  	}
	    }															
	   
	    arbre = new JTree(racine);

	    arbre.addTreeSelectionListener(new TreeListener());
	}
	
	public void initEvent() {
		
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				StringBuilder tex = new StringBuilder(champsMessage.getText());
				
				if (!tex.toString().isEmpty())
				{
					for (int i = 0;i<tex.length();i+=100) {
						tex.insert(i, System.getProperty("line.separator"));
					}
					
					Date dateEnvoie = new Date();
					
					if (ticketSelected != null) {
						
						Message message = new Message(tex.toString(), dateEnvoie, ticketSelected, utilisateur);
						
		            	try {
		            		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
							
		            		objectOutputStream.writeObject(message);
		            		objectOutputStream.flush();
		            		
		            		message.getTicket().getMessages().add(message);
		            		
		   	        	  	updatePane();
		   	        	  
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
		            	
						// envoie du ticket au serveur
						
						addMessage(false, message);
						
						champsMessage.setText("");
						scroll.revalidate();	
					}
				}
			}
		});
		
		deconnexion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		nouvelleConversation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				ajoutTicket.setVisible(true);
			}
		});
	}
	
	public void scrollToBottom()
	{
		scroll(scroll,ScrollDirection.DOWN);
	}
	
	public static void scroll(JScrollPane scrollPane, ScrollDirection direction) {
	    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
	    // If we want to scroll to the top set this value to the minimum, else to the maximum
	    int topOrBottom = direction.equals(ScrollDirection.UP) ? verticalBar.getMinimum() : verticalBar.getMaximum();
	    AdjustmentListener scroller = new AdjustmentListener() {
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            Adjustable adjustable = e.getAdjustable();
	            adjustable.setValue(topOrBottom);
	            // We have to remove the listener since otherwise the user would be unable to scroll afterwards
	            verticalBar.removeAdjustmentListener(this);
	        }
	    };
	    verticalBar.addAdjustmentListener(scroller);
	}
	
	public enum ScrollDirection {
	    UP, DOWN
	}
	
	ArrayList<Ticket> addedTicket = new ArrayList<>();
	
	ArrayList<Message> addedMessage = new ArrayList<>();
	
	public Ticket getTicketById(int id) {
		
		for (Groupe groupe: ActionUtilisateur.getGroupes())
		{
			for (Ticket t: groupe.getTickets())
			{
				if (t.getId() == id)
					return t;
			}
		}
			
		for (Ticket t: addedTicket)
		{
			if (t.getId() == id)
				return t;
		}
		
		return null;
	}
	

	public Groupe getGroupeById(int id) {
		
		for (Groupe groupe: utilisateur.getGroupes())
		{
			if (groupe.getId() == id)
				return groupe;
		}
		
		return null;
	}
	
	public void updatePane() {
		buildTree(utilisateur.getGroupes());
	}
	

	public void updatePane(Message message) {
		
		addedMessage.add(message);
		
		arbre.collapsePath(new TreePath(arbre.getModel().getRoot()));
		
		DefaultTreeModel model = (DefaultTreeModel)arbre.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		
		for (int i = 0;i<root.getChildCount();i++)
		{
			DefaultMutableTreeNode gPath = (DefaultMutableTreeNode)root.getChildAt(i);
		
			if (gPath.toString().equals(message.getTicket().getGroupe().getNom()))
			{
				for (int j = 0;j<gPath.getChildCount();j++)
				{
					DefaultMutableTreeNode tPath = (DefaultMutableTreeNode)gPath.getChildAt(j);
				
					System.out.println(tPath.toString());
					
		        	int idTicket = Integer.valueOf(tPath.toString().split(" - ")[0].trim());
		        	
		        	if (idTicket == message.getTicket().getId())
		        	{
		        		arbre.setSelectionPath(new TreePath(tPath.getPath()));
		        		
		    		  	ecran.removeAll();
			        
		        		send.setEnabled(true);
			        	
		        		ticketSelected = message.getTicket();
				        	
		        		for (Message m :message.getTicket().getMessages())
		        		{ 	
		        			if (m.getExpediteur().getId() == utilisateur.getId())
		        				addMessage(false, m);
		        			else
		        				addMessage(true, m);
		        		}
				        	  
		        		for (Message m :addedMessage)
		        		{ 	
		        			if (m.getTicket().getId() == message.getTicket().getId()) {
		        				if (m.getExpediteur().getId() == utilisateur.getId())
		        					addMessage(false, m);
		        				else
		        					addMessage(true, m);
		        		    }
		        		}
			        
		        		ecran.revalidate();
		        	}
				}
			}
		}
	}
	
	public void updatePane(Ticket ticket) {
		addedTicket.add(ticket);
		
		boolean groupeExiste = false;
		
		for (Groupe g : utilisateur.getGroupes())
		{		
			if (g.getId() == ticket.getGroupe().getId())
				groupeExiste = true;
		}
	
		DefaultTreeModel model = (DefaultTreeModel)arbre.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
 		
		if (groupeExiste) {
			for (int i = 0;i<root.getChildCount();i++)
			{
				DefaultMutableTreeNode g = (DefaultMutableTreeNode)root.getChildAt(i);
			
				if (g.toString().equals(ticket.getGroupe().getNom()))
				{
					g.add(new DefaultMutableTreeNode(ticket.getId()+" - "+ticket.getTitle()));
					break;
				}
			}
		}
		else
		{
			System.out.println(ticket.getGroupe().getNom());
			
			DefaultMutableTreeNode new_groupe = new DefaultMutableTreeNode(ticket.getGroupe().getNom());
			
			root.add(new_groupe);

			new_groupe.add(new DefaultMutableTreeNode(ticket.getId()+" - "+ticket.getTitle()));
		}
		
		model.reload(root);
	}
	
	public class TreeListener implements TreeSelectionListener {
		  @Override
	        public void valueChanged(TreeSelectionEvent e) {
	        
    		  	ecran.removeAll();
	        	TreePath path = e.getPath();
	        	
	        	if (path.getPathCount() == 3)
	        	{
	        		send.setEnabled(true);
	        		
		        	int idTicket = Integer.valueOf(path.toString().split(",")[2].split(" - ")[0].trim());

		        	Ticket ticket = getTicketById(idTicket);
		        	ticketSelected = ticket;
		        	
		        	  for (Message message :ticket.getMessages())
				      { 	
		        		  if (message.getExpediteur().getId() == utilisateur.getId())
		        			  addMessage(false, message);
		        		  else
		        			  addMessage(true, message);
				      }
		        	  
		        	  for (Message message :addedMessage)
				      { 	
		        		  if (message.getTicket().getId() == ticket.getId()) {
			        		  if (message.getExpediteur().getId() == utilisateur.getId())
			        			  addMessage(false, message);
			        		  else
			        			  addMessage(true, message);
		        		  }
				      }
	        	}
	        	else {
	        			send.setEnabled(false);
			        	ticketSelected = null;
	        		}
      		ecran.revalidate();
	        }
	}
}
