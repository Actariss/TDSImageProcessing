package ImageProcessing.Contours;

import ImageProcessing.NonLineaire.MorphoElementaire;

public class ContoursNonLineaire 
{
    public static int[][] gradientErosion(int[][] image)
    {
        int tailleMasque = 3;
        int[][] erodee = MorphoElementaire.erosion(image, tailleMasque);

        int h = image.length;
        int l = image[0].length;
        int[][] gradient = new int[h][l];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                gradient[i][j] = image[i][j] - erodee[i][j];
                if (gradient[i][j] < 0) gradient[i][j] = 0; // évitons d'aller dans les négatifs ;)
        }
    }

    return gradient;
    }

    public static int[][] gradientDilatation(int[][] image)
    {
        int tailleMasque = 3;
        int[][] dilatee = MorphoElementaire.dilatation(image, tailleMasque);

        int h = image.length;
        int l = image[0].length;
        int[][] gradient = new int[h][l];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                gradient[i][j] = dilatee[i][j] - image[i][j];
                if (gradient[i][j] < 0) gradient[i][j] = 0;
            }
        }

        return gradient;
    }

    public static int[][] gradientBeucher(int[][] image)
    {
        int tailleMasque = 3;

        int[][] dilatee = MorphoElementaire.dilatation(image, tailleMasque);
        int[][] erodee = MorphoElementaire.erosion(image, tailleMasque);

        int h = image.length;
        int l = image[0].length;
        int[][] gradient = new int[h][l];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                gradient[i][j] = dilatee[i][j] - erodee[i][j];
                if (gradient[i][j] < 0) gradient[i][j] = 0;
            }
        }

        return gradient;
    }

    public static int[][] laplacienNonLineaire(int[][] image) 
    {
        int tailleMasque = 3;

        int[][] dilatee = MorphoElementaire.dilatation(image, tailleMasque);
        int[][] erodee = MorphoElementaire.erosion(image, tailleMasque);

        int h = image.length;
        int l = image[0].length;
        int[][] laplacien = new int[h][l];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                int val = dilatee[i][j] + erodee[i][j] - 2 * image[i][j];
                laplacien[i][j] = Math.max(0, Math.min(255, val)); // on évite également de dépasser 255
            }
        }

        return laplacien;
    }
    
}
