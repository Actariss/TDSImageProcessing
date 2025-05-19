package ImageProcessing.Lineaire;

public class FiltrageLineaireLocal {

    /**
     * Réalise le filtrage local de l'image en utilisant le masque de convolution.
     * @param image Image d'entrée en niveaux de gris (matrice 2D d'entiers).
     * @param masque Masque de convolution (matrice 2D de doubles).
     * @return Image filtrée.
     */
    public static int[][] filtreMasqueConvolution(int[][] image, double[][] masque) {
        int M = image.length;
        int N = image[0].length;
        int[][] result = new int[M][N];
        
        int tailleM = masque.length;
        int tailleN = masque[0].length;
        
        if (tailleM % 2 == 0 || tailleN % 2 == 0) {
            throw new IllegalArgumentException("Le masque doit avoir des dimensions impaires");
        }
        
        // Calcul des offsets (centre du masque)
        int offsetM = tailleM / 2;
        int offsetN = tailleN / 2;
        
        // Appliquer la convolution 
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                double somme = 0.0;
                
                for (int k = -offsetM; k <= offsetM; k++) {
                    for (int l = -offsetN; l <= offsetN; l++) {
                        // Coordonnées du pixel dans l'image
                        int x = i + k;
                        int y = j + l;
                        
                        if (x < 0) x = Math.abs(x);
                        if (x >= M) x = 2 * M - x - 2;
                        if (y < 0) y = Math.abs(y);
                        if (y >= N) y = 2 * N - y - 2;
                        
                        // Appliquer le coefficient du masque
                        somme += image[x][y] * masque[k + offsetM][l + offsetN];
                    }
                }
                
                int valeur = (int) Math.round(somme);
                
                // Limiter les valeurs entre 0 et 255 (Pour une img sinon caca)
                if (valeur < 0) valeur = 0;
                if (valeur > 255) valeur = 255;
                
                result[i][j] = valeur;
            }
        }
        
        return result;
    }
    
    /**
     * Réalise un filtrage moyenneur de l'image avec un masque de convolution carré.
     * @param image Image d'entrée en niveaux de gris.
     * @param tailleMasque Taille du masque carré (doit être impaire).
     * @return Image filtrée.
     */
    public static int[][] filtreMoyenneur(int[][] image, int tailleMasque) {
        return null;
    }
}