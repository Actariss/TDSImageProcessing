package ImageProcessing.Contours;

import ImageProcessing.Lineaire.FiltrageLineaireLocal;

public class ContoursLineaire 
{
    public static int[][] gradientPrewitt(int[][] image, int dir) 
    {
        double[][] masque;

        switch (dir) {
            case 1 -> // Horizontal
                masque = new double[][] {
                    {-1, 0, 1},
                    {-1, 0, 1},
                    {-1, 0, 1}
                };
            case 2 -> // Vertical
                masque = new double[][] {
                    { 1,  1,  1},
                    { 0,  0,  0},
                    {-1, -1, -1}
                };
            default -> throw new IllegalArgumentException("Direction invalide : doit être 1 (horizontal) ou 2 (vertical)");
        }
        
        return FiltrageLineaireLocal.filtreMasqueConvolution(image, masque);
    }

    public static int[][] gradientSobel(int[][] image, int dir) 
    {
        double[][] masque;

        switch (dir) {
            case 1 -> // Horizontal
                masque = new double[][] {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}
                };
            case 2 -> // Vertical
                masque = new double[][] {
                    { 1,  2,  1},
                    { 0,  0,  0},
                    {-1, -2, -1}
                };
            default -> throw new IllegalArgumentException("Direction invalide : doit être 1 (horizontal) ou 2 (vertical)");
        }

        return FiltrageLineaireLocal.filtreMasqueConvolution(image, masque);
    }

    public static int[][] laplacien4(int[][] image)
    {
        double[][] masque = new double[][] {
            { 0, -1,  0},
            {-1,  4, -1},
            { 0, -1,  0}
        };

        return FiltrageLineaireLocal.filtreMasqueConvolution(image, masque);
    }

    public static int[][] laplacien8(int[][] image)
    {
        double[][] masque = new double[][] {
            {-1, -1, -1},
            {-1,  8, -1},
            {-1, -1, -1}
        };

        return FiltrageLineaireLocal.filtreMasqueConvolution(image, masque);
    }
}
