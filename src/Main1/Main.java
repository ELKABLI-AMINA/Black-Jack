package Main1;

import BlackJack.BlackJack;

import java.util.Scanner;

import static BlackJack.BlackJack.*;

public class Main {
    public static void main(String[] args) {
        // Carte de départ : As de Carreau (1, 1)
        int[] asDeCarreau = {1, 1};
        int[][] jeuDeCartes = createCardDeck();

        for (int[] carte : jeuDeCartes) {
            System.out.print("[" + carte[0] + ", " + carte[1] + "]");
        }
        ///////////////////////////////////////////
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Entrez l'indice de la carte à extraire : ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        int[][][] resultatExtraction = extraireIemeCarte(jeuDeCartes, indice);
        int[] carteExtraite = resultatExtraction[0][0];
        int[][] cartesRestantes = resultatExtraction[1];
        System.out.print("[[");
        System.out.print(carteExtraite[0] + ", " + carteExtraite[1]);
        System.out.print("], [");
        for (int i = 0; i < cartesRestantes.length; i++) {
            System.out.print(cartesRestantes[i][0] + ", " + cartesRestantes[i][1]);
            if (i < cartesRestantes.length - 1) {
                System.out.print("] [");
            }
        }
        System.out.println("]]");

/////////////////////////////////
        int[][][] resultatExtractionT = tirerCarte(jeuDeCartes);
        int[] carteTiree = resultatExtractionT[0][0];
        int[][] cartesRestantesT = resultatExtractionT[1];

        System.out.print("[[");
        System.out.print(carteTiree[0] + ", " + carteTiree[1]);
        System.out.print("], [");
        for (int i = 0; i < cartesRestantesT.length; i++) {
            System.out.print(cartesRestantesT[i][0] + ", " + cartesRestantesT[i][1]);
            if (i < cartesRestantesT.length - 1) {
                System.out.print("] [");
            }
        }
        System.out.println("]]");
      ///////////////////////////::::
      int[][] cartesM=BlackJack.melanger(jeuDeCartes);
        System.out.println("amina");

    }

}

