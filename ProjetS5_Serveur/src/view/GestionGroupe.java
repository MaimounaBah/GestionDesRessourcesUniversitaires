package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Model.ConnexionDB;
import Model.Groupe;
import Model.GroupeDB;

public class GestionGroupe extends JFrame{
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
	static final long serialVersionUID = 1L;
	GroupeDB ugroupeDAO;
	public GestionGroupe(ConnexionDB connexionDB){
		ugroupeDAO = new GroupeDB(connexionDB);
		init();
		initPanel();
		initEvent();
	}
	
	public void init(){
		this.setTitle("Gestion groupe");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
	}	
	JTable tableau;

	Properties p = new Properties();
	public void initPanel(){
		panelEdition = new JPanel();
		panelEdition.setPreferredSize(new Dimension(450,190));
		panelAffichage = new JPanel();
		panelAffichage.setPreferredSize(new Dimension(495,245));
		this.setLayout(new FlowLayout());
		String[] titre = {"Id","Nom"};
		Object[][] groupe = getTabUser();
		tableau = new JTable(new DefaultTableModel(groupe, titre));
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
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		panelEdition.add(panelEditionHaut,BorderLayout.CENTER);
		this.getContentPane().add(panelEdition);	
		this.getContentPane().add(panelAffichage);	
	}
	
	private Object[][] getTabUser() {
		Object[][] groupes = new Object[ugroupeDAO.getAllGroupes().size()][6];
		for(int i=0;i<ugroupeDAO.getAllGroupes().size();i++){
			groupes[i][0] = ugroupeDAO.getAllGroupes().get(i).getId();
			groupes[i][1] = ugroupeDAO.getAllGroupes().get(i).getNom();
		}
		
		return groupes;
	}
	
	int ij = 0;
	public void initEvent()
	{
		ajouter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nom = textFieldNom.getText();
				Groupe user = new Groupe(nom);

				boolean ok = true;
				
				if (nom.isEmpty()){
					ok  = false;
					textFieldNom.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldNom.setBorder(BorderFactory.createLineBorder(Color.BLACK));					
						
				if (ugroupeDAO.isGroupeExiste(nom)){
					ok  = false;
					textFieldNom.setBorder(BorderFactory.createLineBorder(Color.RED));	
				    JOptionPane.showMessageDialog(GestionGroupe.this, "Ce nom de groupe est déjà utilisé!", "Erreur", JOptionPane.ERROR_MESSAGE);

				}
				
				if (ok) {
					
					ugroupeDAO.save(user);
					
					Groupe groupe = ugroupeDAO.getLastInserred();
					
					Object[] donnee = {groupe.getId(),groupe.getNom()};
					((DefaultTableModel)tableau.getModel()).addRow(donnee);
				}
			}
		});
		

		modifier.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableau.getSelectedRow() == -1) {
					return;
				}
				int idGroupe = Integer.valueOf(tableau.getModel().getValueAt(tableau.getSelectedRow(), 0).toString());
				int idRow = tableau.getSelectedRow();			
				JOptionPane jop = new JOptionPane(); 
				
				if (ij++ % 2 == 0){
					try{
						idGroupe = Integer.valueOf(tableau.getModel().getValueAt(tableau.getSelectedRow(), 0).toString());
						updatePanelAffiche(ugroupeDAO.getGroupeById(idGroupe),idRow);
					}
					catch(Exception ee){
					    jop.showMessageDialog(GestionGroupe.this, "Veillez choisir une ligne dans la table", "Erreur", JOptionPane.ERROR_MESSAGE);
						ee.printStackTrace();
					}							
				}
				else{	
					String nom = textFieldNom.getText();
					Groupe groupe = new Groupe(nom);
					ugroupeDAO.updateGroupeById(idGroupe,groupe);
					tableau.getModel().setValueAt(groupe.getNom(), idRow, 1);
					modifier.setText("Modifier");
				}
			}
		});
		
		supprimer.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tableau.getSelectedRow() == -1) {
					return;
				}
				JOptionPane jop = new JOptionPane(); 
				try{
					int idGroupe = Integer.valueOf(tableau.getModel().getValueAt(tableau.getSelectedRow(), 0).toString());
					int idRow = tableau.getSelectedRow();
					int op = jop.showConfirmDialog(null, "Voulez vous vraiment supprimer cette ligne ?", "Suppression", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(op != JOptionPane.NO_OPTION &&  op != JOptionPane.CANCEL_OPTION &&  op != JOptionPane.CLOSED_OPTION){
						ugroupeDAO.deleteLine(idGroupe);										
						((DefaultTableModel)tableau.getModel()).removeRow(idRow);
					} 
				}
				catch(Exception ee){
				     jop.showMessageDialog(GestionGroupe.this, "Veillez choisir une ligne dans la table", "Erreur", JOptionPane.ERROR_MESSAGE);
					ee.printStackTrace();
				}
			}
		});
	}
	
	
	public void updatePanelAffiche(Groupe groupe,int idRow){
		textFieldNom.setText(groupe.getNom());
		modifier.setText("Valider");
	}
}