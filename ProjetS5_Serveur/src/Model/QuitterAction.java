package Model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class QuitterAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	public QuitterAction(String texte) {
		super(texte);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0); //Ferme l'application	
	}

}
