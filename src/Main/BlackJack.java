package Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlackJack {

    public static List<int[]> createCard(int[] carte) {
        List<int[]> cartes = new ArrayList<>();
        Set<Integer> couleurs = new HashSet<>();
        couleurs.add(1); // Carreau
        couleurs.add(2); // Cœur
        couleurs.add(3); // Pique
        couleurs.add(4); // Trèfle
        int prochaineHauteur = carte[0];
        int prochaineCouleur = carte[1];

        while (cartes.size() < 52) {
            cartes.add(new int[]{prochaineHauteur, prochaineCouleur});

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
}