package ImageProcessing.Lineaire;

import ImageProcessing.Complexe.*;
import ImageProcessing.Fourier.Fourier;

public class FiltrageLineaireGlobal {

    /**
     * Applique un filtre passe-bas idéal à l'image via la transformée de Fourier.
     * @param image Image d'entrée en niveaux de gris (matrice 2D d'entiers).
     * @param frequenceCoupure Rayon de la fenêtre circulaire (fréquence de coupure).
     * @return Image filtrée.
     */
    public static int[][] filtrePasseBasIdeal(int[][] image, int frequenceCoupure) {
        int M = image.length;
        int N = image[0].length;

        double[][] f = new double[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                f[i][j] = (double) image[i][j];
            }
        }
        
        MatriceComplexe fourier = Fourier.Fourier2D(f);
        
        fourier = Fourier.decroise(fourier);
        
        int centreM = M / 2;
        int centreN = N / 2;
        
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                // Calculer la distance au centre
                double distance = Math.sqrt((u - centreM) * (u - centreM) + (v - centreN) * (v - centreN));
                
                // Si la distance est supérieure à la fréquence de coupure, mettre à zéro
                if (distance > frequenceCoupure) {
                    fourier.set(u, v, 0.0, 0.0);
                }
            }
        }
        
        fourier = Fourier.decroise(fourier);
        
        MatriceComplexe resultatComplex = Fourier.InverseFourier2D(fourier);
        
        double[][] resultatDouble = resultatComplex.getPartieReelle();
        int[][] result = new int[M][N];
        
        // Normalisation et conversion en entiers
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                
                int valeur = (int) Math.round(resultatDouble[i][j]);
                // Limiter les valeurs entre 0 et 255 (Pour une img sinon caca)
                if (valeur < 0) valeur = 0;
                if (valeur > 255) valeur = 255;
                result[i][j] = valeur;
            }
        }
        
        return result;
        
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
