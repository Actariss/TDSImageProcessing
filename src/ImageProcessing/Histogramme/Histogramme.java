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
}
