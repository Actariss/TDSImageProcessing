package ImageProcessing.Seuillage;

import ImageProcessing.Histogramme.Histogramme;

public class Seuillage 
{
    public static int[][] seuillageSimple(int[][] image, int seuil) 
    {
        int h = image.length;
        int l = image[0].length;
        int[][] result = new int[h][l];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                result[i][j] = (image[i][j] >= seuil) ? 255 : 0;
            }
        }
        return result;
    }

    public static int[][] seuillageDouble(int[][] image, int seuil1, int seuil2)
    {
        int h = image.length;
        int l = image[0].length;
        int[][] result = new int[h][l];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                int val = image[i][j];
                if (val < seuil1)
                    result[i][j] = 0;
                else if (val < seuil2)
                    result[i][j] = 128;
                else
                    result[i][j] = 255;
            }
        }
        return result;
    }

    public static int[][] seuillageAutomatique(int[][] image) 
    {
        int seuil = Histogramme.luminance(image); // Seuil initial: moyenne des NG = luminance !
        boolean stable = false;
        
        while (!stable) {
            int sum1 = 0, count1 = 0;
            int sum2 = 0, count2 = 0;

            for (int[] row : image) {
                for (int pixel : row) {
                    if (pixel < seuil) {
                        sum1 += pixel;
                        count1++;
                    } else {
                        sum2 += pixel;
                        count2++;
                    }
                }
            }

            int m1 = count1 == 0 ? 0 : sum1 / count1;
            int m2 = count2 == 0 ? 0 : sum2 / count2;

            int newSeuil = (m1 + m2) / 2;

            stable = (newSeuil == seuil);
            seuil = newSeuil;
        }
        return seuillageSimple(image, seuil);
    }    
}
