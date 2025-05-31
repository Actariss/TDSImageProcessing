package ImageProcessing.Histogramme;

public class Histogramme 
{
    public static int[] Histogramme256(int mat[][])
    {
        int M = mat.length;
        int N = mat[0].length;
        int histo[] = new int[256];
        
        for(int i=0 ; i<256 ; i++) histo[i] = 0;
        
        for(int i=0 ; i<M ; i++)
            for(int j=0 ; j<N ; j++)
                if ((mat[i][j] >= 0) && (mat[i][j]<=255)) histo[mat[i][j]]++;
        
        return histo;
    }
    public static int minimum(int[][] image)
    {
        int[] histo = Histogramme256(image);
        for (int i = 0; i < histo.length; i++) {
            if (histo[i] > 0) {
                return i;
            }
        }
        return -1;
    }
    public static int maximum(int[][] image)
    {
        int[] histo = Histogramme256(image);
        for (int i = histo.length - 1; i >= 0; i--) {
            if (histo[i] > 0) {
                return i;
            }
        }
        return -1;
    }
    public static int luminance(int[][] image) 
    {
        int[] histo = Histogramme256(image);
        int totalPixels = image.length * image[0].length;
        long somme = 0;

        for (int i = 0; i < histo.length; i++) {
            somme += i * histo[i];
        }
        return (int)(somme / totalPixels);
    }
    public static double contraste1(int[][] image)
    {
        int[] histo = Histogramme256(image);
        int totalPixels = image.length * image[0].length;
        int lumi = luminance(image);
        double somme = 0;

        for (int i = 0; i < histo.length; i++) {
            somme += Math.pow(i - lumi, 2) * histo[i];
        }
        return Math.sqrt(somme / totalPixels);
    }
    public static double contraste2(int[][] image)
    {
        int min = minimum(image);
        int max = maximum(image);

        if ((max + min) == 0) return 0.0;

        return (double)(max - min) / (max + min);
    }
    
    public static int[][] rehaussement(int[][] image, int[] courbeTonale)
    {
        int M = image.length;
        int N = image[0].length;
        int[][] resultat = new int[M][N];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int val = image[i][j];
                resultat[i][j] = courbeTonale[val];
            }
        }

        return resultat;
    }
    public static int[] creeCourbeTonaleLineaireSaturation(int smin, int smax)
    {
        int[] courbe = new int[256];

        for (int i = 0; i < 256; i++) {
            if (i < smin) {
                courbe[i] = 0;
            } else if (i > smax) {
                courbe[i] = 255;
            } else {
                courbe[i] = (int)(((double)(i - smin) / (smax - smin)) * 255);
            }
        }

        return courbe;
    }
    public static int[] creeCourbeTonaleGamma(double gamma) 
    {
        // Ajouter des contraintes pour gamma?
        // Il nous faudrait alors deux fonctions différentes...
        int[] courbe = new int[256];

        for (int i = 0; i < 256; i++) {
            double normalise = (double) i / 255.0;
            double corrige = Math.pow(normalise, 1.0 / gamma);
            courbe[i] = (int) (corrige * 255);
        }

        return courbe;
    }
    public static int[] creeCourbeTonaleNegatif() 
    {
        int[] courbe = new int[256];

        for (int i = 0; i < 256; i++) {
            courbe[i] = 255 - i;
        }

        return courbe;
    }
    public static int[] creeCourbeTonaleEgalisation(int[][] image) {
        int[] histo = Histogramme256(image);
        int totalPixels = image.length * image[0].length;
        double[] p = new double[256];
        double[] F = new double[256];
        int[] courbe = new int[256];

        // Normalisation de l’histogramme
        for (int i = 0; i < 256; i++) {
            p[i] = (double) histo[i] / totalPixels;
        }

        // Calcul des fréquences cumulées
        F[0] = p[0];
        for (int i = 1; i < 256; i++) {
            F[i] = F[i - 1] + p[i];
        }

        // Transformation tonale
        for (int i = 0; i < 256; i++) {
            courbe[i] = (int)(F[i] * 255);
        }

        return courbe;
    }
}
