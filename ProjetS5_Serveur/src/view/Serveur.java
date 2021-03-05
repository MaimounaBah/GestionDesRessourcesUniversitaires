package view;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Model.ConnexionDB;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Serveur extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// menu boutons
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenu edition = new JMenu("Edition");
	private JMenu aide = new JMenu("?");
	
	// Panel
	private JPanel panelCentre;
	private JPanel panelBottom;

	public JTextArea ecran;
	
	// boutton
	JButton deconnexion,boutonGestionUser,boutonGestionGroupe ,boutonGestionAdhesion;
	JScrollPane scroll;
	ConnexionDB connexionDB;
	
	public Serveur(ConnexionDB connexionDB)
	{
		this.connexionDB = connexionDB;
	
		init();
		initMenu();
		initPanel();
		initEvent();
	}
	
	public void init()
	{
		this.setTitle("Serveur");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(900, 500);
		this.setLocationRelativeTo(null);
	}
	
	public void initMenu()
	{
		fichier.add(exit);
		menuBar.add(fichier);
		menuBar.add(edition);	
		menuBar.add(aide);
		
		this.setJMenuBar(menuBar);
	}
	
	public void initPanel()
	{
		panelCentre = new JPanel();	

		panelCentre.setLayout(new BorderLayout());
		
		panelCentre.setBackground(Color.WHITE);
		
		ecran = new JTextArea();
		ecran.setEditable(false);
	
		scroll = new JScrollPane(panelCentre);
		 
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panelCentre.add(ecran,BorderLayout.CENTER);
	
		panelBottom = new JPanel();
		
		panelBottom.setPreferredSize(new Dimension(900, 100));
		
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.LINE_AXIS));
		
		boutonGestionUser = new JButton("Geston utilisateur");
		boutonGestionGroupe = new JButton("Geston groupe");
		boutonGestionAdhesion = new JButton("Geston adhesion");

		panelBottom.add(boutonGestionUser);
		panelBottom.add(boutonGestionGroupe);
		panelBottom.add(boutonGestionAdhesion);
		
		this.getContentPane().add(scroll,BorderLayout.CENTER);
		this.getContentPane().add(panelBottom,BorderLayout.SOUTH);
	}
	
	public void initEvent() {
		boutonGestionUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GestionUtilisateur gestionUtilisateur = new GestionUtilisateur(connexionDB);
				gestionUtilisateur.setVisible(true);
			}
		});
		
		boutonGestionAdhesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				GestionAdhesion gestionAdhesion = new GestionAdhesion(connexionDB);
				gestionAdhesion.setVisible(true);
			}
		});
		
		boutonGestionGroupe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				GestionGroupe gestionGroupe = new GestionGroupe(connexionDB);
				gestionGroupe.setVisible(true);
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
	
	
	
}

