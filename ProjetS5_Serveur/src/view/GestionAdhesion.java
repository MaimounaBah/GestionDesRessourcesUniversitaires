package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Model.ConnexionDB;
import Model.Groupe;
import Model.GroupeDB;
import Model.Utilisateur;
import Model.UtilisateurDB;
import Model.UtilisateurGroupe;
import Model.UtilisateurGroupeDB;

public class GestionAdhesion extends JFrame
{
	/**
	 *	Composants principaux 
	 */
	private JPanel panelLeft,panelRight,panelBas;
	
	private JButton ajouter = new JButton("Ajouter");
	private JButton supprimer = new JButton("Supprimer");

	DefaultListModel<String> modelL1,modelL2;
	JList<String> list1,list2;
	
	static final long serialVersionUID = 1L;
	
	JTable tableau;
	
	Properties p = new Properties();
	
	UtilisateurDB utilisateurDB;
	GroupeDB groupeDB;
	UtilisateurGroupeDB utilisateurGroupeDB;
	ConnexionDB connexionDB;
	
	public GestionAdhesion(ConnexionDB connexionDB)
	{
		this.connexionDB = connexionDB;
		
		utilisateurDB = new UtilisateurDB(connexionDB);
		groupeDB = new GroupeDB(connexionDB);
		utilisateurGroupeDB = new UtilisateurGroupeDB(connexionDB);
		
		init();
		initPanel();
		initEvent();		
	}
	
	public void init()
	{
		this.setTitle("Gestion adhésion");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(700, 700);
		this.setLocationRelativeTo(null);
	}

	String[] titres = {"Nom de l'utilisateur","Nom groupe"};;
	
	public void initPanel(){
		JPanel containerTop = new JPanel();		
		containerTop.setLayout(new GridLayout(2, 1));

		JPanel container = new JPanel();
				
		JPanel utilisateurGroupePanel = new JPanel();
		
		panelLeft = new JPanel();
		
		container.setLayout(new GridLayout(1, 2));
		
		modelL1 = new DefaultListModel<>();  
		  
	  	JScrollPane scrollPaneModelL1 = new JScrollPane();
	  	
	  	for (Utilisateur user : utilisateurDB.getAllUtilisateurs())
	  		modelL1.addElement(user.getId() +" - "+user.getPrenom()+" "+user.getNom());
	  	
	  	list1 = new JList<>(modelL1);  
      
	  	list1.setPreferredSize(new Dimension(280, 500));
	  	scrollPaneModelL1.setViewportView(list1);
		  	
	  	panelLeft.add(scrollPaneModelL1);
      	
	  	modelL2 = new DefaultListModel<>(); 
	  	
	  	JScrollPane scrollPaneModelL2 = new JScrollPane();

      	for (Groupe groupe : groupeDB.getAllGroupes())
      		modelL2.addElement(groupe.getId() +" - "+groupe.getNom());
	  	
      	list2 = new JList<>(modelL2);  
      	
      	scrollPaneModelL2.setViewportView(list2);
	  	list2.setPreferredSize(new Dimension(280, 500));
	  	
		panelRight = new JPanel();
		panelRight.add(scrollPaneModelL2);

		container.add(panelLeft);	
		container.add(panelRight);	
		
		containerTop.add(container);
	
		
		Object[][] UtilisateurGroupe = getTabUserGroupe();
		
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		tableau = new JTable(new DefaultTableModel(UtilisateurGroupe, titres));
		
		utilisateurGroupePanel.add(new JScrollPane(tableau));
		
		containerTop.add(utilisateurGroupePanel);
		
		JLabel titre = new JLabel("Affectation des groupes");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelBas = new JPanel();
		panelBas.setLayout(new GridLayout(2, 1));
		
    	panelBas.add(ajouter);
      	panelBas.add(supprimer);
		
		this.getContentPane().add(titre,BorderLayout.NORTH);	
		this.getContentPane().add(containerTop,BorderLayout.CENTER);
		this.getContentPane().add(panelBas,BorderLayout.SOUTH);
	}

	private Object[][] getTabUserGroupe() {
		ArrayList<UtilisateurGroupe> utilisateurGroupes  = utilisateurGroupeDB.getAllUtilisateursGroupe();
		
		Object[][] groupes = new Object[utilisateurGroupes.size()][6];
		
		for(int i=0;i<utilisateurGroupes.size();i++)
		{
			System.out.println(utilisateurGroupes.get(i).getUtilisateur().getNom());
			
			groupes[i][1] = utilisateurGroupes.get(i).getGroupe().getNom();
			groupes[i][0] = utilisateurGroupes.get(i).getUtilisateur().getPrenom() 
					+" "+ utilisateurGroupes.get(i).getUtilisateur().getNom();
		}
		
		return groupes;
	}
	
	public void initEvent() {
		ajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String>  selectedUsersBr = new ArrayList<>();				
				List<String> selectedGroupesBr = new ArrayList<>();
				selectedUsersBr = list1.getSelectedValuesList();
				selectedGroupesBr = list2.getSelectedValuesList();
				ArrayList<Utilisateur> selectedUsers = extractUsersFromList(selectedUsersBr);
				ArrayList<Groupe> selectedGroupes  = extractGroupesFromList(selectedGroupesBr);
				utilisateurGroupeDB.addUsersToGroupes(selectedUsers,selectedGroupes);
				Object[][] UtilisateurGroupe = getTabUserGroupe();
				tableau.setModel(new DefaultTableModel(UtilisateurGroupe,titres));
			}
		});
		
		supprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String>  selectedUsersBr = new ArrayList<>();				
				List<String> selectedGroupesBr = new ArrayList<>();
				selectedUsersBr = list1.getSelectedValuesList();
				selectedGroupesBr = list2.getSelectedValuesList();
				ArrayList<Utilisateur> selectedUsers = extractUsersFromList(selectedUsersBr);
				ArrayList<Groupe> selectedGroupes  = extractGroupesFromList(selectedGroupesBr);
				System.out.println(selectedUsers.size() + " "+selectedGroupes.size());
				utilisateurGroupeDB.removeUsersToGroupes(selectedUsers,selectedGroupes);
				Object[][] utilisateurGroupe = getTabUserGroupe();
				
				tableau.setModel(new DefaultTableModel(utilisateurGroupe,titres));
			}
		});
	}
	
	public ArrayList<Utilisateur> extractUsersFromList(List<String> liste) {
		
		ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
		
		for (String e: liste)
		{
			if (e.split(" - ")[0] != "")
			{
				int id = Integer.valueOf(e.split(" - ")[0]);
				Utilisateur utilisateur = utilisateurDB.getUtilisateurById(id);
				if (utilisateur!= null)
					utilisateurs.add(utilisateur);
			}
		}
		
		return utilisateurs;
	}
	
	public ArrayList<Groupe> extractGroupesFromList(List<String> liste) {
		
		ArrayList<Groupe> groupes = new ArrayList<>();
		
		for (String e: liste)
		{
			if (e.split(" - ")[0] != "")
			{
				int id = Integer.valueOf(e.split(" - ")[0]);
				
				Groupe groupe =groupeDB.getGroupeById(id);
				if (groupe!= null)
					groupes.add(groupe);
			}
		}
		
		return groupes;
	}

}
