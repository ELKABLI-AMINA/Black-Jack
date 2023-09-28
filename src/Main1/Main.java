package Main1;

import BlackJack.BlackJack;

import java.util.Scanner;

import static BlackJack.BlackJack.*;

public class Main {
    public static void main(String[] args) {
        int[][] cartes = createCardDeck();
        int[] argentJoueur = {1000};
        int[] argentCroupier = {1000};

        while (argentJoueur[0] > 0) {
            System.out.println("Solde du joueur : " + argentJoueur[0]);
            int mise = miser(argentJoueur[0]);

            distribuerCartesInitiales(cartes, "Joueur", "Croupier", argentJoueur, argentCroupier);

            System.out.println("Voulez-vous continuer à jouer ? (1 pour Oui, 2 pour Non)");
            Scanner scanner = new Scanner(System.in);
            int choix = scanner.nextInt();
            if (choix == 2) {
                break;
            }
        }
        System.out.println("Merci d'avoir joué !");
    }


}

