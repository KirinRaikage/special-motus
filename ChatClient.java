
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	public BufferedReader in;
	public PrintWriter out;
	private Fenetre affichage;
	public Socket socket;
	public ChatClient() {
		String serverAddress = "127.0.0.1";

		
		try {
			socket = new Socket(serverAddress, 9001);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		affichage = new Fenetre(out);
	}

	/**
	 * Connects to the server then enters the processing loop.
	 */
	private void run() throws IOException {

		// Make connection and initialize streams
		int processInscription = 1;
		boolean processJeu = true;
		String str = "";

		// Process all messages from server, according to the protocol.
		while (processInscription == 1) {

			String message_entrant = in.readLine().toUpperCase();
			if (message_entrant.startsWith("CHOIX")) {
				out.println(affichage.choixInscriptionSelection());
				// out.println(choixInscriptionSelection());
			} else if (message_entrant.startsWith("INSCRIPTION")) {
				out.println(affichage.selectionNom());
			} else if (message_entrant.startsWith("CONNEXION")) {
				out.println(affichage.selectionCodeLicence());
			} else if (message_entrant.startsWith("DEJAINSCRIS")) {
				affichage.dejaInscris();
			} else if (message_entrant.startsWith("PROCESS_INSCRIPTION_TERMINE")) {
				affichage.inscriptionValidee(message_entrant);
				processInscription = 0;
			} else if (message_entrant.startsWith("PROCESS_CONNEXION_TERMINE")) {
				affichage.connexionValidee(message_entrant.substring(25));
				processInscription = 0;
			} else if (message_entrant.startsWith("MOT DE PASSE INCORRECT")) {
				affichage.motDePasseIncorrect();
			}
		} // end while processIncription

		while (processJeu) {

			String message_entrant = in.readLine();
			if (message_entrant.startsWith("GAGNE")) {
				// affichage.afficherScore();
				str = affichage.choixContinuerQuitter();
				out.println(str);
				
				if(str.equals("1")){
					processJeu = false;
					socket.close();
					affichage.fermerFenetre();
				}
			} else if (message_entrant.startsWith("REESSAYER")) {
				affichage.modifierInterface(message_entrant.substring(9));
			}

		}
	}

	/**
	 * Exc�cute le client comme une fen�tre fermable
	 */
	public static void main(String[] args) throws Exception {
		ChatClient client = new ChatClient();
		client.run();
	}
}