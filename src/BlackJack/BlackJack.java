package BlackJack;

import java.util.Random;
import java.util.Scanner;

public class BlackJack {
    public static int mise = 0;
    public static int argentJoueur=0;
     public static int scoreJoueur = 0;
    public static int scoreCroupier = 0;
    public static int[][] createCardDeck(int[] asDeCarreau) {
        int[][] cartes = new int[52][2];

        int prochaineHauteur = asDeCarreau[0];
        int prochaineCouleur = asDeCarreau[1];

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
        int[][] carteExtraite = new int[1][2];
        int[][] cartesRestantes = new int[cartes.length - 1][2];

        int j = 0;
        for (int i = 0; i < cartes.length; i++) {
            if (i != indice) {
                cartesRestantes[j++] = cartes[i];
            } else {
                carteExtraite[0][0] = cartes[i][0];
                carteExtraite[0][1] = cartes[i][1]; 
            }
        }

        int[][][] resultat = new int[2][][];
        resultat[0] = carteExtraite;
        resultat[1] = cartesRestantes;

        return resultat;
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
        int[][] carteTiree = new int[52][];
        int[][] cartesRestantes = cartes.clone(); // Clonez le tableau original pour ne pas le modifier.

        for (int i = 0; i < cartes.length; i++) {
            int[][][] resultatExtractionT = tirerCarte(cartesRestantes);
            carteTiree[i] = resultatExtractionT[0][0];
            cartesRestantes = resultatExtractionT[1];
        }


        return carteTiree;
    }

    public static int[][][] piocherCartes(int[][] cartes) {
        Random random = new Random();
         int n = random.nextInt(11) + 20; // Génère un nombre aléatoire entre 20 et 30 inclus

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
    public static int[][] distribuerCartes(int[][] cartes, String joueur, String croupier, int argentJoueur) {
        int[][] carteJoueur = new int[2][];
        int[][] carteCroupier = new int[2][];
        cartes = melanger(cartes);
        int[][][] carteTireeJoueur = piocherCartes(cartes);
        int [][]CartesPourJouer = carteTireeJoueur[0];
        int scoreJoueur = 0;

        for (int i = 0; i < 2; i++) {
            int valeurCarteJoueur = CartesPourJouer[i][0];
            valeurCarteJoueur = ConditionJoueur(valeurCarteJoueur);
            scoreJoueur += valeurCarteJoueur;
            carteJoueur[i] = new int[] { CartesPourJouer[i][0], CartesPourJouer[i][1] };
        }

        for (int i = 0; i < 2; i++) {
            int valeurCarteCroupier = CartesPourJouer[i + 2][0];
            valeurCarteCroupier = ConditionCroupier(valeurCarteCroupier);
            scoreCroupier += valeurCarteCroupier;
            carteCroupier[i] = new int[] { CartesPourJouer[i + 2][0], CartesPourJouer[i + 2][1] };
        }

        System.out.println(croupier + "  : [" + carteCroupier[0][0] + ", " + carteCroupier[0][1] + "] et  [?, ?]");
        System.out.println(joueur + "  : [" + carteJoueur[0][0] + ", " + carteJoueur[0][1] + "] et [" + carteJoueur[1][0] + ", " + carteJoueur[1][1] + "]");

        scoreJoueur = phaseJoueur(cartes, scoreJoueur, argentJoueur);
        if (scoreJoueur <= 21) {
            phaseCroupier(cartes, scoreCroupier, scoreJoueur);
        }
        determinerResultat(argentJoueur, scoreJoueur, scoreCroupier, mise);


        int cartesRestantesCount = CartesPourJouer.length - 4;
        int[][] cartesRestantesAprèsDistribution = new int[cartesRestantesCount][2];
        for (int i = 4; i < CartesPourJouer.length; i++) {
            cartesRestantesAprèsDistribution[i - 4] = new int[] { CartesPourJouer[i][0], CartesPourJouer[i][1] };
        }
        return cartesRestantesAprèsDistribution;
    }
    public static int phaseJoueur(int[][] cartesRestantesAprèsDistribution, int scoreInitialJoueur, int argentJoueur) {
        int scoreAfficheJoueur = scoreInitialJoueur;
        int scoreJoueur = scoreInitialJoueur;

        Scanner scanner = new Scanner(System.in);

        while (scoreJoueur < 21 && cartesRestantesAprèsDistribution.length > 0) {
            System.out.println("Score du Joueur : " + scoreAfficheJoueur);
            System.out.print("Voulez-vous demander une autre carte ? (Oui/Non): ");
            String choix = scanner.nextLine().trim().toLowerCase();

            if (choix.equals("oui")) {
                int indiceCarte = (int) (Math.random() * cartesRestantesAprèsDistribution.length);
                int valeurCarteJoueur = cartesRestantesAprèsDistribution[indiceCarte][0];

                ConditionJoueur(valeurCarteJoueur);
                scoreJoueur += valeurCarteJoueur;

                System.out.println("Joueur a tiré une carte de valeur " + valeurCarteJoueur);

                cartesRestantesAprèsDistribution = retirerCarte(cartesRestantesAprèsDistribution, indiceCarte);
            } else if (choix.equals("non")) {
                break;
            } else {
                System.out.println("Veuillez répondre par 'Oui' ou 'Non'.");
            }

            scoreAfficheJoueur = scoreJoueur;
        }

        return scoreJoueur;
    }
    public static int[][] retirerCarte(int[][] cartesRestantesAprèsDistribution, int indice) {
        int[][] nouvellesCartes = new int[cartesRestantesAprèsDistribution.length - 1][2];

        for (int i = 0, j = 0; i < cartesRestantesAprèsDistribution.length; i++) {
            if (i != indice) {
                nouvellesCartes[j++] = cartesRestantesAprèsDistribution[i];
            }
        }

        return nouvellesCartes;
    }
    public static int phaseCroupier(int[][] cartesRestantesAprèsDistribution, int scoreInitialCroupier, int scoreJoueur) {
        int scoreCroupier = scoreInitialCroupier;

        while (scoreCroupier < 17 && cartesRestantesAprèsDistribution.length > 0) {
            System.out.println("Score du Croupier : " + scoreCroupier);
            int indiceCarte = (int) (Math.random() * cartesRestantesAprèsDistribution.length);
            int valeurCarteCroupier = cartesRestantesAprèsDistribution[indiceCarte][0];

            ConditionCroupier(valeurCarteCroupier);

            System.out.println("Croupier a tiré une carte de valeur " + valeurCarteCroupier);

            scoreCroupier += valeurCarteCroupier;

            cartesRestantesAprèsDistribution = retirerCarte(cartesRestantesAprèsDistribution, indiceCarte);
        }

        return scoreCroupier;
    }



    public static void determinerResultat(int argentJoueur, int scoreJoueur, int scoreCroupier, int mise) {
        if (scoreJoueur > 21) {
            System.out.println("Joueur dépasse 21. Croupier gagne !");
            System.out.println(" le solde du Joueur est : " + (argentJoueur-mise));

        } else if (scoreCroupier > 21) {
            System.out.println("Croupier dépasse 21. Joueur gagne !");

        } else if (scoreJoueur == scoreCroupier) {
            System.out.println("Égalité. Argent retourné au joueur.");

        } else if (scoreJoueur == 21) {
            System.out.println("Joueur a un blackjack !");
            System.out.println(" le solde du Joueur est : " + (argentJoueur+mise));
        } else if (scoreCroupier == 21) {
            System.out.println("Croupier a un blackjack. Croupier gagne !");
        } else if (scoreJoueur > scoreCroupier) {
            System.out.println("Joueur gagne !");
            System.out.println(" le solde du Joueur est : " + (argentJoueur+mise));
        } else {
            System.out.println("Croupier gagne !");
        }


    }
    public static int ConditionJoueur(int valeurCarteJoueur){
        if (valeurCarteJoueur >= 10) {
            return 10;
        }
        if (valeurCarteJoueur == 1) {
            if (scoreCroupier + 11 <= 10) {
                return 11;
            } else {
                return 1;
            }
        }
        return valeurCarteJoueur;
    }

    public static int ConditionCroupier(int valeurCarteCroupier){
        if (valeurCarteCroupier >= 10) {
            return 10;
        }
        if (valeurCarteCroupier == 1) {
            if (scoreCroupier + 11 <= 10) {
                return 11;
            } else {
                return 1;
            }
        }
        return valeurCarteCroupier;
    }


}
































