package ImageProcessing.Utils;

import CImage.CImageRGB;
import CImage.Exceptions.CImageRGBException;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static int[][] extraireCanal(CImageRGB imageRGB, String couleur) throws CImageRGBException
    {
        int h = imageRGB.getHauteur();
        int l = imageRGB.getLargeur();
        
        int[][] output = new int[l][h];
        
        switch (couleur.toLowerCase()) {
            case "r" -> imageRGB.getMatricesRGB(output, null, null);
            case "g" -> imageRGB.getMatricesRGB(null, output, null);
            case "b" -> imageRGB.getMatricesRGB(null, null, output);
        }
        return output;
    }
    public static boolean isBinary(int[][] matrice) {
        Set<Integer> valeursMatrice;
        valeursMatrice = new HashSet<>();
        int M = matrice.length;
        int N = matrice[0].length;
        for(int i=0 ; i<M ; i++) {
            for(int j=0 ; j<N ; j++) {
                valeursMatrice.add(matrice[i][j]);
                if (valeursMatrice.size() > 2){
                    return false;
                }
            }
        }
        return true;
    }
}
