package BlackJack;

import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    public static void createCard(int[] asDeCarreau) {

        int[][] jeuDeCartes = createCardDeck();

        for (int[] carte : jeuDeCartes) {
            System.out.print("[" + carte[0] + ", " + carte[1] + "]");
        }
    }

    public static int[][] createCardDeck() {
        int[][] cartes = new int[52][2];
        int[] couleurs = {1, 2, 3, 4};

        int prochaineHauteur = 1;
        int prochaineCouleur = 1;

        for (int index = 0; index < 52; index++) {
            cartes[index][0] = prochaineHauteur;
            cartes[index][1] = prochaineCouleur;

            prochaineHauteur++;
            if (prochaineHauteur > 13) {
                prochaineHauteur = 1;
                prochaineCouleur++;
            }

            if (prochaineCouleur > 4) {
                prochaineCouleur = 1;
            }
        }

        return cartes;
    }

    public static int[][][] extraireIemeCarte(int[][] cartes, int indice) {
        if (indice >= 0 && indice < cartes.length) {
            int[] carteExtraite = cartes[indice];
            int[][] cartesRestantes = new int[cartes.length - 1][2];

            int j = 0;
            for (int i = 0; i < cartes.length; i++) {
                if (i != indice) {
                    cartesRestantes[j++] = cartes[i];
                }
            }
            int[][][] resultat = new int[2][][];

            resultat[0] = new int[1][];
            resultat[1] = cartesRestantes;

            resultat[0][0] = carteExtraite;


            return resultat;
        } else {
            System.out.println("L'indice n'existe pas");
            return null;
        }
    }

    public static int[][][] tirerCarte(int[][] cartes) {
        if (cartes.length > 0) {
            Random random = new Random();
            int indice = random.nextInt(cartes.length);
            return extraireIemeCarte(cartes, indice);
        } else {
            System.out.println("L'indice n'existe pas");
            return null;
        }
    }

    public static int[][] melanger(int[][] cartes) {
        int[][][] resultatExtractionT = tirerCarte(cartes);
        int[][] carteTiree = new int[52][];
        carteTiree[0] = resultatExtractionT[0][0];
        int[][] cartesRestantesT = resultatExtractionT[1];
        for (int i = 0; i < cartes.length - 1; i++) {
            resultatExtractionT = tirerCarte(cartesRestantesT);
            carteTiree[++i] = resultatExtractionT[0][0];
            cartesRestantesT = resultatExtractionT[1];
        }
        return carteTiree;
    }

    public static int[][][] piocherCartes(int[][] cartes, int n) {

        int[][] cartePiochees = new int[n][];
        int[][] cartesRestantes = new int[cartes.length - n][];

        for (int i = 0; i < n; i++) {
            cartePiochees[i] = cartes[i];
        }
        for (int i = n; i < cartes.length; i++) {
            cartesRestantes[i - n] = cartes[i];
        }

        int[][][] resultat = new int[2][][];
        resultat[0] = cartePiochees;
        resultat[1] = cartesRestantes;

        return resultat;

    }

    public static int[][] defausserCartes(int[][] cartePiochees, int[][] cartes_a_defausser) {
        int taille_pioche = cartePiochees.length;
        int taille_defausse = cartes_a_defausser.length;

        int[][] pioche_apres_defausse = new int[taille_pioche + taille_defausse][2];

        for (int i = 0; i < taille_pioche; i++) {
            pioche_apres_defausse[i] = cartePiochees[i];
        }

        for (int i = 0; i < taille_defausse; i++) {
            pioche_apres_defausse[taille_pioche + i] = cartes_a_defausser[i];
        }

        return pioche_apres_defausse;
    }

    public static int miser(int argentJoueur) {

        int mise = 0;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Faites votre mise : ");
            mise = scanner.nextInt();
            if (mise <= 0 || mise > argentJoueur) {
                System.out.println("Mise invalide. Veuillez miser un montant entre 1 et " + argentJoueur);
            }
        } while (mise <= 0 || mise > argentJoueur);

        argentJoueur -= mise;

        return mise;
    }

    public static void distribuerCartesInitiales(int[][] cartes, String joueur, String croupier, int[] argentJoueur, int[] argentCroupier) {
        int[][] carteJoueur = new int[2][];
        int[][] carteCroupier = new int[2][];
        int scoreJoueur = 0;
        int scoreCroupier = 0;

        for (int i = 0; i < 2; i++) {
            int[][][] carteTireeJoueur = tirerCarte(cartes);
            int[][][] carteTireeCroupier = tirerCarte(cartes);

            int valeurCarteJoueur = carteTireeJoueur[0][0][0];
            int valeurCarteCroupier = carteTireeCroupier[0][0][0];

            scoreJoueur += valeurCarteJoueur;
            scoreCroupier += valeurCarteCroupier;

            carteJoueur[i] = carteTireeJoueur[0][0];
            carteCroupier[i] = carteTireeCroupier[0][0];
        }

        System.out.println(croupier + "  : [" + carteCroupier[0][0] + ", " + carteCroupier[0][1] + "] et [" + carteCroupier[1][0] + ", " + carteCroupier[1][1] + "]");
        System.out.println(joueur + "  : [" + carteJoueur[0][0] + ", " + carteJoueur[0][1] + "] et [" + carteJoueur[1][0] + ", " + carteJoueur[1][1] + "]");

        phaseJoueur(cartes, scoreJoueur, argentJoueur, argentCroupier);
        phaseCroupier(cartes, scoreCroupier, argentCroupier);
        determinerResultat(argentJoueur, argentCroupier, scoreJoueur, scoreCroupier);
    }

    public static void phaseCroupier(int[][] cartes, int scoreInitialCroupier, int[] argentCroupier) {
        int scoreCroupier = scoreInitialCroupier;

        while (true) {
            System.out.println("Score du Croupier : " + scoreCroupier);

            if (scoreCroupier >= 17) {
                System.out.println("Le croupier s'arrête.");
                break;
            }

            System.out.println("Croupier, voulez-vous demander une autre carte ? (1 pour Oui, 2 pour Non)");
            Scanner scanner = new Scanner(System.in);
            int choix = scanner.nextInt();

            if (choix == 1) {
                int[][][] carteTireeCroupier = tirerCarte(cartes);
                int valeurCarteCroupier = carteTireeCroupier[0][0][0];
                scoreCroupier += valeurCarteCroupier;
                System.out.println("Croupier a tiré une carte de valeur " + valeurCarteCroupier);
                System.out.println("Nouveau score du Croupier : " + scoreCroupier);
            } else if (choix == 2) {
                System.out.println("Le croupier s'arrête.");
                break;
            }
        }
    }

    public static void phaseJoueur(int[][] cartes, int scoreInitialJoueur, int[] argentJoueur, int[] argentCroupier) {
        int scoreAfficheJoueur = scoreInitialJoueur;
        int scoreJoueur = scoreInitialJoueur;

        while (scoreJoueur < 21) {
            System.out.println("Score du Joueur : " + scoreAfficheJoueur);

            System.out.println("Voulez-vous demander une autre carte ? (1 pour Oui, 2 pour Non)");
            Scanner scanner = new Scanner(System.in);
            int choix = scanner.nextInt();

            if (choix == 1) {
                int[][][] carteTireeJoueur = tirerCarte(cartes);
                int valeurCarteJoueur = carteTireeJoueur[0][0][0];

                scoreJoueur += valeurCarteJoueur;
                System.out.println("Joueur a tiré une carte de valeur " + valeurCarteJoueur);

                if (scoreJoueur > 21) {
                    System.out.println("Vous avez dépassé 21. Vous ne pouvez pas demander de carte supplémentaire.");
                    break;
                }

                scoreAfficheJoueur = scoreJoueur;
            } else if (choix == 2) {
                break;
            }
        }

        System.out.println("Score du Joueur : " + scoreAfficheJoueur);


        System.out.println("Solde du joueur : " + argentJoueur[0]);
    }

    public static void determinerResultat(int[] argentJoueur, int[] argentCroupier, int scoreJoueur, int scoreCroupier) {
        if (scoreJoueur > 21) {
            System.out.println("Joueur dépasse 21. Croupier gagne !");
        } else if (scoreCroupier > 21) {
            System.out.println("Croupier dépasse 21. Joueur gagne !");
            argentJoueur[0] += 2 * argentJoueur[0];
        } else if (scoreJoueur == scoreCroupier) {
            System.out.println("Égalité. Argent retourné au joueur.");
            argentJoueur[0] += argentJoueur[0];
        } else if (scoreJoueur == 21) {
            System.out.println("Joueur a un blackjack !");
            argentJoueur[0] += (int)(2.5 * argentJoueur[0]);
        } else if (scoreCroupier == 21) {
            System.out.println("Croupier a un blackjack. Croupier gagne !");
        } else if (scoreJoueur > scoreCroupier) {
            System.out.println("Joueur gagne !");
            argentJoueur[0] += 2 * argentJoueur[0];
        } else {
            System.out.println("Croupier gagne !");
        }


        System.out.println("Solde du croupier : " + argentCroupier[0]);
    }




}
































