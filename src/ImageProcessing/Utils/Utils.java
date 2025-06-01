package ImageProcessing.Utils;

import CImage.CImageRGB;
import CImage.Exceptions.CImageRGBException;

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
}
