package Main1;

import BlackJack.BlackJack;

import java.util.Scanner;

import static BlackJack.BlackJack.*;

public class Main {
    public static void main(String[] args) {

        int[][] jeuDeCartes = createCardDeck();
        String croupier = "Croupier";
        String joueur = "Joueur";
        int argentJoueur = 1000;
        while(argentJoueur>0){
            int mise = miser(argentJoueur);
            distribuerCartesInitiales(jeuDeCartes,croupier,joueur);



        }


    }
}

