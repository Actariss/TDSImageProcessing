package ImageProcessing.NonLineaire;

public class MorphoElementaire {

    /**
     * Erosion morphologique
     * @param image : image à traiter (niveaux de gris ou binaire)
     * @param tailleMasque : taille impaire du masque structurant (ex: 3, 5, 7...)
     * @return image érodée
     */
    public static int[][] erosion(int[][] image, int tailleMasque) {
    int largeur = image.length;
        int hauteur = image[0].length;
        int[][] resultat = new int[largeur][hauteur];

        int demiMasque = tailleMasque / 2;

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                int min = 255; // Valeur max en niveaux de gris

                for (int i = -demiMasque; i <= demiMasque; i++) {
                    for (int j = -demiMasque; j <= demiMasque; j++) {
                        int nx = x + i;
                        int ny = y + j;

                        if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur) {
                            min = Math.min(min, image[nx][ny]);
                        }
                    }
                }

                resultat[x][y] = min;
            }
        }

        return resultat;
    }

    /**
     * Dilatation morphologique
     * @param image : image à traiter
     * @param tailleMasque : taille impaire du masque structurant
     * @return image dilatée
     */
    public static int[][] dilatation(int[][] image, int tailleMasque) {
        // TODO : Implémenter la dilatation (max dans le voisinage)
        return null;
    }

    /**
     * Ouverture : érosion suivie de dilatation
     * @param image : image à traiter
     * @param tailleMasque : taille impaire
     * @return image ouverte
     */
    public static int[][] ouverture(int[][] image, int tailleMasque) {
        int[][] eroded = erosion(image, tailleMasque);
        return dilatation(eroded, tailleMasque);
    }

    /**
     * Fermeture : dilatation suivie d'érosion
     * @param image : image à traiter
     * @param tailleMasque : taille impaire
     * @return image fermée
     */
    public static int[][] fermeture(int[][] image, int tailleMasque) {
        int[][] dilated = dilatation(image, tailleMasque);
        return erosion(dilated, tailleMasque);
    }
}
