package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Model.ConnexionDB;
import Model.Utilisateur;
import Model.UtilisateurDB;

public class GestionUtilisateur extends JFrame
{
	/**
	 *	Composants principaux 
	 */

	private JPanel panelEdition = new JPanel();
	private JPanel panelAffichage = new JPanel();
	private JPanel panelEditionHaut = new JPanel();
	private JPanel panelEditionBas = new JPanel();
	private JButton ajouter = new JButton("Ajouter");
	private JButton supprimer = new JButton("Supprimer");
	private JButton modifier = new JButton("Modifier");
	
	private JPanel panelNom = new JPanel();
	private JLabel labelNom = new JLabel("Nom: ");	
	private JTextField textFieldNom = new JTextField();	
	
	private JPanel panelPrenom = new JPanel();
	private JLabel labelPrenom = new JLabel("Prénom: ");	
	private JTextField textFieldPrenom = new JTextField();	
	
	private JPanel panelUsername = new JPanel();
	private JLabel labelUsername = new JLabel("Nom d'utilisateur: ");	
	private JTextField textFieldUsername = new JTextField();	

	private JPanel panelPassword = new JPanel();
	private JLabel labelPassword = new JLabel("Mot de passe: ");	
	private JPasswordField textFieldPassword = new JPasswordField();	
	
	static final long serialVersionUID = 1L;
	
	UtilisateurDB utilisateurDB;
	
	public GestionUtilisateur(ConnexionDB connexionDB)
	{
		utilisateurDB = new UtilisateurDB(connexionDB);
		
		init();
		initPanel();
		initEvent();	
	}
	
	public void init()
	{
		this.setTitle("Gestion utilisateur");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
	}	
	
	JTable tableau;
	
	Properties p = new Properties();
	public void initPanel()
	{
		panelEdition = new JPanel();
		panelEdition.setPreferredSize(new Dimension(450,190));
		panelAffichage = new JPanel();
		panelAffichage.setPreferredSize(new Dimension(495,245));
		
		this.setLayout(new FlowLayout());
		
		String[] titre = {"Id","Nom","Prénom","Nom d'utilisateur"};
		
		Object[][] utilisateur = getTabUser();
		
		tableau = new JTable(new DefaultTableModel(utilisateur, titre));
		
		panelAffichage.add(new JScrollPane(tableau));
		
		panelEdition.setLayout(new BorderLayout());
			panelEditionBas.add(ajouter);
			panelEditionBas.add(supprimer);
			panelEditionBas.add(modifier);
		panelEdition.add(panelEditionBas,BorderLayout.SOUTH);
		
		panelNom.add(labelNom);
		textFieldNom.setPreferredSize(new Dimension(100,30));
		panelNom.add(textFieldNom);		
		panelEditionHaut.add(panelNom);
		
		
		panelPrenom.add(labelPrenom);
		textFieldPrenom.setPreferredSize(new Dimension(100,30));
		panelPrenom.add(textFieldPrenom);		
		panelEditionHaut.add(panelPrenom);
		
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		panelUsername.add(labelUsername);
		textFieldUsername.setPreferredSize(new Dimension(100,30));
		panelUsername.add(textFieldUsername);		
		panelEditionHaut.add(panelUsername);

		panelPassword.add(labelPassword);
		textFieldPassword.setPreferredSize(new Dimension(100,30));
		panelPassword.add(textFieldPassword);		
		panelEditionHaut.add(panelPassword);
		
		panelEdition.add(panelEditionHaut,BorderLayout.CENTER);
			
		this.getContentPane().add(panelEdition);	
		this.getContentPane().add(panelAffichage);	
	}
	
	private Object[][] getTabUser() {
		Object[][] utilisateurs = new Object[utilisateurDB.getAllUtilisateurs().size()][6];
		try {
			for(int i=0;i<utilisateurDB.getAllUtilisateurs().size();i++)
			{
				utilisateurs[i][0] = utilisateurDB.getAllUtilisateurs().get(i).getId();
				utilisateurs[i][1] = utilisateurDB.getAllUtilisateurs().get(i).getNom();
				utilisateurs[i][2] = utilisateurDB.getAllUtilisateurs().get(i).getPrenom();
				utilisateurs[i][3] = utilisateurDB.getAllUtilisateurs().get(i).getUsername();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return utilisateurs;
	}
	
	int ij = 0;
	public void initEvent()
	{
		ajouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String nom = textFieldNom.getText();
				String prenom = textFieldPrenom.getText();
				String username = textFieldUsername.getText();
				@SuppressWarnings("deprecation")
				String password = textFieldPassword.getText();
				
				Utilisateur user = new Utilisateur(nom, prenom, username, password,new ArrayList<>());

				boolean ok = true;
				
				if (nom.isEmpty()) 
				{
					ok  = false;
					textFieldNom.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldNom.setBorder(BorderFactory.createLineBorder(Color.BLACK));					
				
				if (prenom.isEmpty())				
				{
					ok  = false;
					textFieldPrenom.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldPrenom.setBorder(BorderFactory.createLineBorder(Color.BLACK));					
				
				if (username.isEmpty()) {						
					ok  = false;
					textFieldUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK));					
				
				if (password.isEmpty()) {
					ok  = false;
					textFieldPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK));					
				
				if (utilisateurDB.isUserExiste(username))
				{
					ok  = false;
					textFieldUsername.setBorder(BorderFactory.createLineBorder(Color.RED));
					
				    JOptionPane.showMessageDialog(GestionUtilisateur.this, "Ce nom d'utilisateur est déjà utilisé!", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
					
				if (ok)
					if (password.length() <= 6)
					{
						ok = false;
						JOptionPane.showMessageDialog(GestionUtilisateur.this, "Votre mot de passe doit avoir au moins 6 caractères", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				
				if (ok) {
					
					utilisateurDB.save(user);
					
					Utilisateur utilisateur = utilisateurDB.getLastInserred();
					
					Object[] donnee = {utilisateur.getId(),utilisateur.getNom(),utilisateur.getPrenom(),utilisateur.getUsername()};
					((DefaultTableModel)tableau.getModel()).addRow(donnee);
				}
			}
		});
		

		modifier.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				int idUser = Integer.valueOf(tableau.getModel().getValueAt(tableau.getSelectedRow(), 0).toString());
				int idRow = tableau.getSelectedRow();			
				
				JOptionPane jop = new JOptionPane(); 
				
				if (ij++ % 2 == 0)
				{
					try
					{
						idUser = Integer.valueOf(tableau.getModel().getValueAt(tableau.getSelectedRow(), 0).toString());
						updatePanelAffiche(utilisateurDB.getUtilisateurById(idUser),idRow);
					}
					catch(Exception ee)
					{
					    jop.showMessageDialog(GestionUtilisateur.this, "Veillez choisir une ligne dans la table", "Erreur", JOptionPane.ERROR_MESSAGE);
						ee.printStackTrace();
					}							
				}
				else
				{	
					String nom = textFieldNom.getText();
					String prenom = textFieldPrenom.getText();
					String username = textFieldUsername.getText();
					
					
					Utilisateur utlisateur = new Utilisateur(nom, prenom, username);
					
					utilisateurDB.updateUtilisateurById(idUser,utlisateur);
					
					tableau.getModel().setValueAt(utlisateur.getNom(), idRow, 1);
					tableau.getModel().setValueAt(utlisateur.getPrenom(), idRow, 2);
					tableau.getModel().setValueAt(utlisateur.getUsername(), idRow, 3);
					
					modifier.setText("Modifier");
				}
			}
		});
		
		supprimer.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane(); 
				try
				{
					int idUser = Integer.valueOf(tableau.getModel().getValueAt(tableau.getSelectedRow(), 0).toString());
					
					
					int idRow = tableau.getSelectedRow();
					int op = jop.showConfirmDialog(null, "Voulez vous vraiment supprimer cette ligne ?", "Suppression", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				
					if(op != JOptionPane.NO_OPTION &&    op != JOptionPane.CANCEL_OPTION &&    op != JOptionPane.CLOSED_OPTION)
					{
						utilisateurDB.deleteLine(idUser);										
						((DefaultTableModel)tableau.getModel()).removeRow(idRow);
					} 
				}
				catch(Exception ee)
				{
				     jop.showMessageDialog(GestionUtilisateur.this, "Veillez choisir une ligne dans la table", "Erreur", JOptionPane.ERROR_MESSAGE);
					ee.printStackTrace();
				}
			}
		});
	}
	
	
	public void updatePanelAffiche(Utilisateur utilisateur,int idRow)
	{
		textFieldNom.setText(utilisateur.getNom());
		textFieldPrenom.setText(utilisateur.getPrenom());
		textFieldUsername.setText(utilisateur.getUsername());
		
		modifier.setText("Valider");
	}
}