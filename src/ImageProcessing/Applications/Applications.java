package ImageProcessing.Applications;

import CImage.CImageRGB;
import CImage.Exceptions.CImageRGBException;
import ImageProcessing.Histogramme.Histogramme;
import ImageProcessing.NonLineaire.MorphoComplexe;
import ImageProcessing.Utils.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static CImageRGB application2a(CImageRGB image) throws CImageRGBException
    {
        try {
            // Extraction R G B en différents NG
            int[][] canalR = Utils.extraireCanal(image, "r");
            int[][] canalG = Utils.extraireCanal(image, "g");
            int[][] canalB = Utils.extraireCanal(image, "b");
            // Egalisation de l'histogramme
            int[] courbeR = Histogramme.creeCourbeTonaleEgalisation(canalR);
            int[] courbeG = Histogramme.creeCourbeTonaleEgalisation(canalG);
            int[] courbeB = Histogramme.creeCourbeTonaleEgalisation(canalB);
            int[][] egalCanalR = Histogramme.rehaussement(canalR, courbeR);
            int[][] egalCanalG = Histogramme.rehaussement(canalG, courbeG);
            int[][] egalCanalB = Histogramme.rehaussement(canalB, courbeB);
            // Assembler
            CImageRGB imageRGBRecombinee = new CImageRGB(egalCanalR, egalCanalG, egalCanalB);
            return imageRGBRecombinee;
        } catch (CImageRGBException ex) {
            Logger.getLogger(Applications.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Si on  s'est foiré
        return null;
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
