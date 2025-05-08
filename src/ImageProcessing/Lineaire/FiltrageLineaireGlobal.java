package ImageProcessing.Lineaire;

public class FiltrageLineaireGlobal {

    /**
     * Applique un filtre passe-bas idéal à l'image via la transformée de Fourier.
     * @param image Image d'entrée en niveaux de gris (matrice 2D d'entiers).
     * @param frequenceCoupure Rayon de la fenêtre circulaire (fréquence de coupure).
     * @return Image filtrée.
     */
    public static int[][] filtrePasseBasIdeal(int[][] image, int frequenceCoupure) {
        // TODO : Appliquer la FFT sur l'image
        // TODO : Construire un masque passe-bas idéal (cercle de rayon frequenceCoupure)
        // TODO : Appliquer le masque à la transformée
        // TODO : Appliquer la transformée inverse (iFFT)
        // TODO : Retourner l'image en niveaux de gris
        return null;
    }

    /**
     * Applique un filtre passe-haut idéal à l'image via la transformée de Fourier.
     * @param image Image d'entrée en niveaux de gris.
     * @param frequenceCoupure Rayon de la fenêtre circulaire (fréquence de coupure).
     * @return Image filtrée.
     */
    public static int[][] filtrePasseHautIdeal(int[][] image, int frequenceCoupure) {
        // TODO : FFT + masque passe-haut (zone circulaire centrée exclue)
        // TODO : iFFT + reconstruction image
        return null;
    }

    /**
     * Applique un filtre passe-bas de Butterworth à l'image.
     * @param image Image d'entrée en niveaux de gris.
     * @param frequenceCoupure Rayon de la fréquence de coupure.
     * @param ordre Ordre du filtre Butterworth.
     * @return Image filtrée.
     */
    public static int[][] filtrePasseBasButterworth(int[][] image, int frequenceCoupure, int ordre) {
        // TODO : FFT + création du filtre Butterworth passe-bas
        // TODO : Application du filtre + iFFT
        return null;
    }

    /**
     * Applique un filtre passe-haut de Butterworth à l'image.
     * @param image Image d'entrée.
     * @param frequenceCoupure Rayon de la fréquence de coupure.
     * @param ordre Ordre du filtre.
     * @return Image filtrée.
     */
    public static int[][] filtrePasseHautButterworth(int[][] image, int frequenceCoupure, int ordre) {
        // TODO : FFT + création du filtre Butterworth passe-haut
        // TODO : Application du filtre + iFFT
        return null;
    }
}
