package Main;

import java.util.List;

import static Main.BlackJack.createCard;

public class main {
    public static void main(String[] args) {
        // Carte de départ
        int[] asDeCarreau = { 1, 1 };

        // Construisez la liste des cartes suivantes à partir de l'As de C
        List<int[]> jeuDeCartes = createCard(asDeCarreau);

        // Affichez le jeu de cartes
        for (int[] carte : jeuDeCartes) {
            System.out.print("[" + carte[0] + ", " + carte[1] + "]");
        }

    }
}
