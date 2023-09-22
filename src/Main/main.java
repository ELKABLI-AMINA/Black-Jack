import static Main.BlackJack.createCard;

public class main {
    public static void main(String[] args) {
        // Carte de départ : As de Carreau (1, 1)
        int[] asDeCarreau = {1, 1};

        // Construisez le jeu de cartes sous forme de tableau normal (int[][])
        int[][] jeuDeCartes = createCard(asDeCarreau);

        // Affichez le jeu de cartes
        for (int[] carte : jeuDeCartes) {
            // Affichez la valeur et la couleur de chaque carte entre crochets
            System.out.print("[" + carte[0] + ", " + carte[1] + "]");
        }
    }
}
