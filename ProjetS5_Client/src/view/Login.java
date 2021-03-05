package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controler.AuthentificationDuClient;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JPanel panelLogin = new JPanel();
	JLabel labelLogin = new JLabel("Nom d'utilisateur (*) : ");	
	JTextField textFieldLogin = new JTextField();	
	
	JPanel panelPassword = new JPanel();
	JLabel labelPassword = new JLabel("Mot de passe (*)  : ");	
	JPasswordField textFieldPassword = new JPasswordField();	
		
	JPanel panelSubmit = new JPanel();	
	public JButton boutonSubmit = new JButton("Se connecter");	
	
	public JLabel labelError = new JLabel("Mot de passe incorrecte");	

	Socket socket;
	
	public Login(Socket socket)
	{
		this.socket = socket;
		init();
		initPanel();
		initEvent();
	}
	
	public void init()
	{
		this.setTitle("Authentification");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setSize(400,200); 
	    this.setLocationRelativeTo(null); 
	    this.setResizable(false);
	}
	
	public void initPanel()
	{
		JPanel container = new JPanel();

			panelLogin.setLayout(new FlowLayout());
			panelLogin.add(labelLogin);
			textFieldLogin.setPreferredSize(new Dimension(180, 30));
			panelLogin.add(textFieldLogin);
			
			panelPassword.setLayout(new FlowLayout());
			panelPassword.add(labelPassword);
			textFieldPassword.setPreferredSize(new Dimension(180, 30));
		
			panelPassword.add(textFieldPassword);
			
			container.setLayout(new FlowLayout());
			
			container.add(panelLogin);
			container.add(panelPassword);
			
			container.add(labelError);
			labelError.setVisible(false);
			panelSubmit.add(boutonSubmit);
		
		this.setLayout(new BorderLayout());
		
		this.getContentPane().add(container,BorderLayout.CENTER);
		this.getContentPane().add(panelSubmit,BorderLayout.SOUTH);
	}
	
	Thread t1;
	
	public void initEvent() {
		
		boutonSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boutonSubmit.setEnabled(false);
				boolean ok = true;
				
				String username = textFieldLogin.getText();
				@SuppressWarnings("deprecation")
				String motdepasse = textFieldPassword.getText();
				
				if (username.isEmpty()) {
					ok  = false;
					textFieldLogin.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldLogin.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				if (motdepasse.isEmpty()) {
					ok  = false;
					textFieldPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else
					textFieldPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				if (ok)
				{
					t1 = new Thread(new AuthentificationDuClient(socket,username,motdepasse,Login.this));
					t1.start();
				}
				else
				{
					boutonSubmit.setEnabled(true);
				}
			}
		});
	}
}

