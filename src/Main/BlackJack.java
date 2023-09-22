package Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlackJack {

    public static int[][] createCard(int[] carte) {
        int[][] cartes = new int[52][2]; // Tableau pour stocker les cartes
        int[] couleurs = {1, 2, 3, 4}; // Tableau pour stocker les couleurs

        int prochaineHauteur = carte[0];
        int prochaineCouleur = carte[1];
        int index = 0;

        while (index < 52) {
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

            index++;
        }

        return cartes;
    }

}