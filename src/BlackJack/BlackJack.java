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
            return extraireIemeCarte(cartes , indice);
        } else {
            System.out.println("L'indice n'existe pas");
            return null;
        }
    }

    public static int[][] melanger(int [][]cartes){
            int[][][] resultatExtractionT = tirerCarte(cartes);
            int[][] carteTiree = new int[52][];
            carteTiree[0]= resultatExtractionT[0][0];
            int[][] cartesRestantesT = resultatExtractionT[1];
            for(int i=0;i<cartes.length-1;i++){
                resultatExtractionT = tirerCarte(cartesRestantesT);
                carteTiree[++i]=resultatExtractionT[0][0];
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

        System.out.println("Solde actuel : " + argentJoueur);
        int mise = 0;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Faites votre mise : ");
            mise = scanner.nextInt();
        } while (mise <= 0 || mise > argentJoueur);

        argentJoueur -= mise;

        return mise;
    }

    public static int[][][] distribuerCartes(int[][] cartes) {
        int[][] mainJoueur = new int[2][];
        int[][] mainCroupier = new int[2][];
        int[][][] resultat = piocherCartes(cartes, 1);
        mainJoueur[0] = resultat[0][0];
        cartes = resultat[1];

        resultat = piocherCartes(cartes, 1);
        mainCroupier[0] = resultat[0][0];
        cartes = resultat[1];


        resultat = piocherCartes(cartes, 1);
        mainJoueur[1] = resultat[0][0];
        cartes = resultat[1];

        // Distribuer une carte face visible pour le croupier
        resultat = piocherCartes(cartes, 1);
        mainCroupier[1] = resultat[0][0];
        cartes = resultat[1];

        int[][][] mains = new int[][][]{mainJoueur, mainCroupier};
        return mains;
    }













}




























