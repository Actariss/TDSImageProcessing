package ImageProcessing.Applications;

import CImage.CImageNG;
import CImage.CImageRGB;
import CImage.Exceptions.CImageNGException;
import CImage.Exceptions.CImageRGBException;
import ImageProcessing.Histogramme.Histogramme;
import ImageProcessing.NonLineaire.MorphoComplexe;
import ImageProcessing.NonLineaire.MorphoElementaire;
import ImageProcessing.Seuillage.Seuillage;
import ImageProcessing.Utils.Utils;
import java.util.HashMap;
import java.util.Map;
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
    }
    public static CImageRGB application2b(CImageRGB image, CImageNG imageNG) throws CImageRGBException, CImageNGException
    {
        // Extraction R G B en différents NG
        int[][] canalR = Utils.extraireCanal(image, "r");
        int[][] canalG = Utils.extraireCanal(image, "g");
        int[][] canalB = Utils.extraireCanal(image, "b");
        // Prendre courbe tonale de image en NG
        int[][] matriceNG = imageNG.getMatrice();
        int[] courbeNG = Histogramme.creeCourbeTonaleEgalisation(matriceNG);
        int[][] egalCanalR = Histogramme.rehaussement(canalR, courbeNG);
        int[][] egalCanalG = Histogramme.rehaussement(canalG, courbeNG);
        int[][] egalCanalB = Histogramme.rehaussement(canalB, courbeNG);
        // Assembler
        CImageRGB imageRGBRecombinee = new CImageRGB(egalCanalR, egalCanalG, egalCanalB);
        return imageRGBRecombinee;
    }
    
    public static Map<String,int[][]> application3(CImageRGB image) throws CImageRGBException
    {
        // On retire le vert avec canalG
        int[][] canalR = Utils.extraireCanal(image, "r");
        int[][] canalG = Utils.extraireCanal(image, "g");
        int[][] canalB = Utils.extraireCanal(image, "b");
        // Il reste un peu des restes de vert en clair: on utilise un négatif + seuil
        int[] courbeNeg = Histogramme.creeCourbeTonaleNegatif();
        int[][] negatifR = Histogramme.rehaussement(canalR, courbeNeg);
        int[][] negatifG = Histogramme.rehaussement(canalG, courbeNeg);
        int[][] negatifB = Histogramme.rehaussement(canalB, courbeNeg);
        int[][] seuillage = Seuillage.seuillageSimple(negatifG, 255);
        // On retire les petits pois par erosion
        int[][] erosion = MorphoElementaire.erosion(seuillage, 5);
        // On reconstruit les gros pois rouges et bleus
        int[][] reconstructionR = MorphoComplexe.reconstructionGeodesique(erosion,negatifB);
        int[][] reconstructionB = MorphoComplexe.reconstructionGeodesique(erosion,negatifR);
        
        Map<String, int[][]> result = new HashMap<>();
        result.put("R",reconstructionR);
        result.put("B",reconstructionB);
        return result;
    }
    
    private static int[][] appliquerMasque(int[][] imageOriginale, int[][] masque) {
        int M = imageOriginale.length;
        int N = imageOriginale[0].length;
        int[][] result = new int[M][N];
        
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (masque[i][j] == 255) {
                    result[i][j] = imageOriginale[i][j];
                } else {
                    result[i][j] = 0; // Fond noir
                }
            }
        }
        
        return result;
    }
    
    
    private static int[][] soustraction(int[][] origine, int[][] aSoustraire)
    {
        // TODO
        int l = origine.length;
        int h = origine[0].length;
        int[][] res = new int[l][h];
        for(int i=0;i<l;i++)
            for(int j=0;j<h;j++)
                res[i][j] = Math.max(0, origine[i][j] - aSoustraire[i][j]);
        return res;
    }
    
    
    public static Map<String,int[][]> application4(int[][] image) {
        int[][] binaire = Seuillage.seuillageAutomatique(image);

        int[][] marqueur = MorphoElementaire.erosion(binaire, 11);
        int[][] grosMasque = MorphoComplexe.reconstructionGeodesique(marqueur, binaire);


        int[][] petitsMasque = soustraction(binaire, grosMasque);

        grosMasque = MorphoElementaire.fermeture(grosMasque, 3);
        petitsMasque = MorphoElementaire.ouverture(petitsMasque, 3);

        int[][] gros = appliquerMasque(image, grosMasque);
        int[][] petits = appliquerMasque(image, petitsMasque);

        Map<String,int[][]> res = new HashMap<>();
        res.put("GROS", gros);
        res.put("PETITS", petits);
        return res;
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
