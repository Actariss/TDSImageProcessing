package ImageProcessing.NonLineaire;

public class MorphoElementaire {

    /**
     * Erosion morphologique
     * @param image : image à traiter (niveaux de gris ou binaire)
     * @param tailleMasque : taille impaire du masque structurant (ex: 3, 5, 7...)
     * @return image érodée
     */
    public static int[][] erosion(int[][] image, int tailleMasque) {
        // TODO : Implémenter l'érosion (min dans le voisinage)
        return null;
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
