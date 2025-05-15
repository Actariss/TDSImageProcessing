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

        // Convertir l'image en matrice de doubles au lieu de Int 
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
        int M = image.length;
        int N = image[0].length;
        
        // Convertir l'image en matrice de doubles au lieu de Int 
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
        
        // Appliquer le filtre passe-haut idéal
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                // Calculer la distance au centre
                double distance = Math.sqrt((u - centreM) * (u - centreM) + (v - centreN) * (v - centreN));
                
                // Si la distance est inférieure ou égale à la fréquence de coupure, mettre à zéro
                if (distance <= frequenceCoupure) {
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
     * Applique un filtre passe-bas de Butterworth à l'image.
     * @param image Image d'entrée en niveaux de gris.
     * @param frequenceCoupure Rayon de la fréquence de coupure.
     * @param ordre Ordre du filtre Butterworth.
     * @return Image filtrée.
     */
    public static int[][] filtrePasseBasButterworth(int[][] image, int frequenceCoupure, int ordre) {
        int M = image.length;
        int N = image[0].length;

        // Convertir l'image en matrice de doubles au lieu de Int 
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

        // Appliquer le filtre de Butterworth passe-bas
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                // Calculer la distance au centre
                double distance = Math.sqrt((u - centreM) * (u - centreM) + (v - centreN) * (v - centreN));

                // Calculer la fonction de transfert de Butterworth 
                double H = 1.0 / (1.0 + Math.pow(distance / frequenceCoupure, 2 * ordre));

                // Multiplier le spectre par la fonction de transfert
                Complexe valeur = fourier.get(u, v);
                double partieReelle = valeur.getPartieReelle() * H;
                double partieImaginaire = valeur.getPartieImaginaire() * H;

                fourier.set(u, v, partieReelle, partieImaginaire);
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
     * Applique un filtre passe-haut de Butterworth à l'image.
     * @param image Image d'entrée.
     * @param frequenceCoupure Rayon de la fréquence de coupure.
     * @param ordre Ordre du filtre.
     * @return Image filtrée.
     */
    public static int[][] filtrePasseHautButterworth(int[][] image, int frequenceCoupure, int ordre) {
        int M = image.length;
        int N = image[0].length;

        // Convertir l'image en matrice de doubles au lieu de Int 
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

        // Appliquer le filtre de Butterworth passe-bas
        for (int u = 0; u < M; u++) {
            for (int v = 0; v < N; v++) {
                // Calculer la distance au centre
                double distance = Math.sqrt((u - centreM) * (u - centreM) + (v - centreN) * (v - centreN));

                // Calculer la fonction de transfert de Butterworth 
                double H = 1.0 / (1.0 + Math.pow(frequenceCoupure / distance, 2 * ordre));

                // Multiplier le spectre par la fonction de transfert
                Complexe valeur = fourier.get(u, v);
                double partieReelle = valeur.getPartieReelle() * H;
                double partieImaginaire = valeur.getPartieImaginaire() * H;

                fourier.set(u, v, partieReelle, partieImaginaire);
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
}
