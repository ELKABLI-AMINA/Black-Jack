package BlackJack;

import java.util.Random;

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

}




























