package Main1;

import BlackJack.BlackJack;

import java.util.Scanner;

import static BlackJack.BlackJack.*;

public class Main {
    public static void main(String[] args) {
        int[] asDeCarreau = {1, 1};
        int[][] jeucartes = createCardDeck(asDeCarreau);
        System.out.println("Entrez votre solde initial : ");
        Scanner scanner= new Scanner(System.in);
        argentJoueur =  scanner.nextInt();

        while (argentJoueur > 0) {

            miser(argentJoueur);
            distribuerCartes(jeucartes, "Joueur", "Croupier", argentJoueur);
            if (argentJoueur <= 0) {
                break;
            }
            System.out.println("Voulez-vous continuer à jouer ? (1 pour Oui, 2 pour Non)");
            scanner = new Scanner(System.in);
            int choix = scanner.nextInt();
            if (choix == 2) {
                break;
            }
        }
        System.out.println("Merci d'avoir joué !");
    }


}

