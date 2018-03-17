
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fenetre extends JFrame {
	private JPanel container = new JPanel();
	private JPanel entete = new JPanel();
	private JPanel corps = new JPanel();
	private int nbLettreMalPlacee = 0;
	private int nbLettreBienPlacee = 0;
	private JTextField LettreMauvaisOrdre = new JTextField("Nombre de lettre mals placées: " + nbLettreMalPlacee);
	private JTextField LettreBonOrdre = new JTextField("Nombre de lettre biens placées: " + nbLettreBienPlacee);
	private JTextField jtf = new JTextField();
	private JButton quitter = new JButton("exit");
	private JLabel label = new JLabel("Mot de 5 lettres: ");
	private JLabel labelnom = new JLabel("nom");
	private JLabel labelprenom = new JLabel("prenom");
	private JLabel labelscore = new JLabel("score 1050 points ");
	private JButton soumettre = new JButton("soumettre");
	private String[] choix = { "Inscription", "Connexion" };
	private String[] choixContinuerQuitter = { "Continuer", "Quitter" };

	public Fenetre(PrintWriter out) {
		this.setTitle("Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(300, 250));

		container.setBackground(Color.BLUE);
		container.setLayout(new BorderLayout());
		corps.setLayout(new BorderLayout());

		LettreMauvaisOrdre.setBackground(Color.yellow);
		LettreBonOrdre.setBackground(Color.red);

		LettreMauvaisOrdre.setPreferredSize(new Dimension(300, 50));
		LettreBonOrdre.setPreferredSize(new Dimension(300, 50));

		jtf.setPreferredSize(new Dimension(150, 30));
		corps.add(LettreMauvaisOrdre, BorderLayout.NORTH);
		corps.add(LettreBonOrdre, BorderLayout.CENTER);
		JPanel center = new JPanel();
		center.add(label);
		center.add(jtf);
		corps.add(center, BorderLayout.SOUTH);

		entete.add(labelnom);
		entete.add(labelprenom);
		entete.add(labelscore);

		container.add(entete, BorderLayout.NORTH);
		container.add(corps, BorderLayout.CENTER);
		JPanel south = new JPanel();
		south.add(soumettre);
		soumettre.addActionListener(new ActionListener() { // Ajouté par Fafa

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				out.println("TENTATIVE" + jtf.getText());
			}
		});
		
		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		south.add(quitter);
		container.add(south, BorderLayout.SOUTH);

		this.setContentPane(container);
		this.setVisible(true);
	}

	public String selectionNom() {
		return JOptionPane.showInputDialog(this, "Choisissez un nom:", "Selection du nom", JOptionPane.PLAIN_MESSAGE);
	}

	public String selectionCodeLicence() {
		return JOptionPane.showInputDialog(this, "Entrez votre code Licence:", "Selection code licence",
				JOptionPane.PLAIN_MESSAGE);
	}

	public String choixInscriptionSelection() {
		return String.valueOf(JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", "Acceuil",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]));
	}

	public void inscriptionValidee(String numeroInscription) {
		String str = "Votre code Licence est: " + numeroInscription.substring(27) + ". Conservez le !";
		JOptionPane.showMessageDialog(this, str, "Code Licence", JOptionPane.INFORMATION_MESSAGE);
	}

	
	public void dejaInscris() {
		JOptionPane.showMessageDialog(this, "Vous êtes déjà inscris ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	public void motDePasseIncorrect() {
		JOptionPane.showMessageDialog(this, "Le code licence ne correspond pas !", "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	public void connexionValidee(String str) {
		JOptionPane.showMessageDialog(this, "Connexion établie. \n Heureux de vous revoir " + str , "Connexion", JOptionPane.INFORMATION_MESSAGE);
	}

	public void afficherScore() {

	}
	
	public void fermerFenetre() {
		this.dispose();
	}

	public void modifierInterface(String message) {
		LettreMauvaisOrdre.setText(null);
		LettreMauvaisOrdre.setText("Nombre de lettre mals placées: " + message.charAt(1));
		LettreBonOrdre.setText(null);
		LettreBonOrdre.setText("Nombre de lettre biens placées:" + message.charAt(0));
	}

	public String choixContinuerQuitter() {
		return String.valueOf(
				JOptionPane.showOptionDialog(null, "Que voulez-vous faire ?", "Hello", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, choixContinuerQuitter, choixContinuerQuitter[0]));
	}

}
