package ImageProcessing.Applications;

import ImageProcessing.NonLineaire.MorphoComplexe;

public class Applications 
{
    public static int[][] application1image1(int[][] image)
    {
        // Possiblement améliorable
        int[][] resultat = MorphoComplexe.filtreMedian(image, 3);
        resultat = MorphoComplexe.filtreMedian(resultat, 7);
        return resultat;
    }
    public static int[][] application1image2(int[][] image)
    {
        int[][] resultat = MorphoComplexe.filtreMedian(image, 3);
        resultat = MorphoComplexe.filtreMedian(resultat, 3);
        return resultat;
    }
    
    public static void application2a(int[][] image)
    {
        // TODO
    }
    public static void application2b(int[][] image)
    {
        // TODO
    }
    
    public static void application3(int[][] image)
    {
        // TODO
    }
    
    public static void application4(int[][] image)
    {
        // TODO
    }
    
    public static void application5(int[][] image)
    {
        // TODO
    }
    
    public static void application6(int[][] image)
    {
        // TODO
    }
    
    public static void application7(int[][] image)
    {
        // TODO
    }
}
