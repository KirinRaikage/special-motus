
import com.sun.xml.internal.bind.v2.TODO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Un jeu multithreaded
 */
public class Serveur {

	/**
	 * Le port sur lequel le serveur �coutera
	 */
	private static final int PORT = 9001;

	/**
	 * Liste des clients d�j� inscris.
	 */
	private static ArrayList<clientProcessor> clientsInscrits = new ArrayList<clientProcessor>();

	/**
	 * Liste des mots d�j� g�n�r�s
	 */
	private static ArrayList<Mot> mots = new ArrayList<Mot>();

	/**
	 * Le main m�thode de l'application. Ecoute sur le port et ouvre des thread de
	 * ServeurProcessor
	 */
	public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }
    public void clearAll() {
	    if (clientsInscrits.isEmpty()) {
            for (int i = 0; i < clientsInscrits.size() ; i++) {
                clientsInscrits.clear();
            }
        }
    }

	/**
	 * Cette classe communique avec un unique client.
	 */
	private static class Handler extends Thread {

		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		/**
		 * Constructeur de notre ServeurProcessor Tout le code int�ressant est trait�
		 * dans la m�thode Run
		 */
		public Handler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Cr�er un code client. Ce code sera unique, il ne peut pas y avoir de doublons
		 * 
		 * @param nom
		 * @return
		 */
		private int creationCodeLicence(String nom) {
			int code = 0;
			boolean resterBoucle = true;
			Random r = new Random();

			while (resterBoucle) {
				resterBoucle = false;

				// on cr�ait un code al�atoire
				code = r.nextInt(10000);

				// si ce code existe d�j� on reste dans la boucle pour cr�er un nouveau code
				synchronized (clientsInscrits) {
					for (clientProcessor c : clientsInscrits) {
						if (c.getCodeLicence() == code)
							resterBoucle = true;
					} // end for
				} // end synchronized
			} // end while
			return code;
		}

		/**
		 * renvoie vrai si le nom du client a d�j� �t� saisie renvoie faux sinon
		 * 
		 * @param nom
		 * @return
		 */
		public boolean nomExisteDeja(String nom) {
			synchronized (clientsInscrits) {
				for (clientProcessor l : clientsInscrits) {
					if (l.getNom().equals(nom)) {
						return true;
					}
				}
			}
			return false;
		}
		public int calculScorePartie(long tempsDepart) {
		  int score=0;
          long duree=((System.currentTimeMillis()-tempsDepart)/1000)/60; //Variable qui stocke la durée stockée en minutes (conformément au CdC)
          if (duree < 1) {
              score = 10;
          }
          else if (duree > 1 && duree < 3) {
              score = 5;
          }
          else if (duree >= 3 && duree <= 5) {
              score = 3;
          }
          else {
              score = 1;
          }
          return score;
        }

		public void run() {
			try {
				String choix;
				String nomRecu;
				String nombreRecu;
				String nomClient = null;
				boolean estPasConnecte;
				boolean mdpCorrect;
				int code = 0;
				long tempsActuel = 0; //Variable qui stocke le temps actuel (en ms)

				// instanciation des objets qui nous servirons au dialogue avec notre client
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				// tant que l'utilisateur n'est pas connect� =)
				estPasConnecte = true;
				while (estPasConnecte) {

					// on demande au client s'il veut se connecter ou s'inscrire
					out.println("CHOIX");
					choix = in.readLine();

					switch (choix) {
					// s'il veut s'inscrire
					case "0":

						// on lui demande son pseudo
						out.println("INSCRIPTION");
						nomRecu = in.readLine();

						// si le nom existe d�j�
						if (nomExisteDeja(nomRecu)) {
							// on informe le client que le nom existe d�j�
							out.println("DEJAINSCRIS");
						} else { // sinon si le pseudo est valide.
							// on cr�ait un code client
							code = creationCodeLicence(nomRecu);

							// on l'ajoute � nos clients d�j� inscrits
							clientsInscrits.add(new clientProcessor(nomRecu, code));

							// System.out.println("on cr�ait le client et son code client est " + code);
							// on informe que l'inscription est termin�e
							// on communique �galement le code au client
							out.println("PROCESS_INSCRIPTION_TERMINE" + code);

							// on passe le boolean a false pour sortir de la boucle
							estPasConnecte = false;
						} // end if
						break;

					// si le client veut se connecter
					case "1":
						// on l'invite a saisir son code client
						out.println("CONNEXION");
						nombreRecu = in.readLine();

						mdpCorrect = false;
						// si il existe un code client correspond, on passe le boolean a true
						synchronized (clientsInscrits) {
							for (clientProcessor l : clientsInscrits) {
								if (String.valueOf(l.getCodeLicence()).equals(nombreRecu)) {
									mdpCorrect = true;
									// on garde le nom du client pour plutard
									nomClient = l.getNom();
								}
							}
						}

						// si le code est valide
						if (mdpCorrect) {
							// on passe le boolean a false pour sortir de la boucle
							estPasConnecte = false;

							// on garde en m�moire le code du client pour pouvoir le retrouver plutard
							code = Integer.valueOf(nombreRecu);

							// on l'information que le connexion est termin�e
							out.println("PROCESS_CONNEXION_TERMINE" + nomClient);
						} else { // sinon le code n'est pas valide
							// on informe l'utilisateur
							out.println("MOT DE PASSE INCORRECT");
						}
						break;
					default:
						break;
					}
				} // end while(estPasConnecte)

				boolean partieContinuer = true;
				boolean motNonTrouve = true;

				// tant que l'utilisateur veut jouer
				while (partieContinuer) {
					// on g�n�re un nouveau mot OU on reprends le mot auquel l'utilisatuer c'�tait
					// arret�
					String motUtilisateur = "";
					Mot motInconnu = new Mot();
					int lettreBienPlacee = 0;
					int lettreMalPlacee = 0;

					for (clientProcessor l : clientsInscrits) {
						if (l.getCodeLicence() == code) {
							// si le joueur a trouv� autant de mot que le nombre de mots existant, on en
							// cr�ait un nouveau
							if (l.getNombreMotTrouve() == mots.size()) {
								motInconnu.chaineAleatoire();
								mots.add(motInconnu);
							}

							// sinon on lui lance le mot auquel il s'est arrete
							else {
								motInconnu = mots.get(l.getNombreMotTrouve());
							}
							System.out.println(motInconnu.getCombinaison());
						}
					}

					// tant que l'utilisateur n'a pas trouv� le mot
					motNonTrouve = true;
					while (motNonTrouve) {
					    tempsActuel=System.currentTimeMillis(); //Stocke le temps actuel

						System.out.println("attente d'un mot");
						String message_entrant = in.readLine().toUpperCase(); //Convertit en majuscules
						if (message_entrant.startsWith("TENTATIVE")) {
							motUtilisateur = message_entrant.substring(9);
							lettreBienPlacee = motInconnu.nbBienPlace(motUtilisateur);
							lettreMalPlacee = motInconnu.nbMalPlace(motUtilisateur);

							System.out
									.println("bien placee: " + lettreBienPlacee + " et mal placee " + lettreMalPlacee);
							if (lettreBienPlacee == 5) {
								out.println("GAGNE");
                                int scorePartie = calculScorePartie(tempsActuel);
                                //TODO Il faudra modifier le score du joueur
								for (clientProcessor l : clientsInscrits) {
								    if (l.getCodeLicence() == code) {
                                        l.setScore(l.getScore()+scorePartie);
                                        out.print("AFFICHER_SCORE");
                                    }

								}
								for (clientProcessor l : clientsInscrits) {
									if (l.getCodeLicence() == code) {
										l.ajouterMotTrouve();
									}
								}

								motNonTrouve = false;

								// on attends la r�ponse s'il le user veut continuer ou quitter
								message_entrant = in.readLine();
								if (message_entrant.equals("1")) {
									partieContinuer = false;
								}

							} else {
								out.println("REESSAYER" + lettreBienPlacee + lettreMalPlacee);
							}
						}
					} // endwhile motNonTrouve
				} // endwhile partieContinuer

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				//si le user s'en va.
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}