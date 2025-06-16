package ImageProcessing.Applications;

import CImage.CImageNG;
import CImage.CImageRGB;
import CImage.Exceptions.CImageNGException;
import CImage.Exceptions.CImageRGBException;
import ImageProcessing.Histogramme.Histogramme;
import ImageProcessing.NonLineaire.MorphoComplexe;
import ImageProcessing.NonLineaire.MorphoElementaire;
import ImageProcessing.Contours.ContoursNonLineaire;
import ImageProcessing.Seuillage.Seuillage;
import ImageProcessing.Utils.Utils;
import java.util.HashMap;
import java.util.Map;

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
        for(int i=0;i<l;i++) {
            for(int j=0;j<h;j++) {
                res[i][j] = Math.max(0, origine[i][j] - aSoustraire[i][j]);
            }
        }
        return res;
    }
    public static int[][] addition(int[][] origine, int[][] aAdditionner) {
        int imageHeight = origine.length;
        int imageWidth = origine[0].length;
        
        int[][] output = new int[imageHeight][imageWidth];
        
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                output[i][j] = Math.min(255, origine[i][j] + aAdditionner[i][j]);
            }
        }
        return output;
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
    
    
    
    public static Map<String,int[][]> application5(int[][] image)
    {
        // Égalistion de l'Histogramme
        int[] courbe = Histogramme.creeCourbeTonaleEgalisation(image);
        int[][] eq = Histogramme.rehaussement(image, courbe);
        // Seuillage zones claires
        int[][] seuil220 = Seuillage.seuillageSimple(eq, 220);
        // Seuillage (récupération de la clé)
        int[][] seuil160 = Seuillage.seuillageSimple(eq, 159);
        // Ouverture géodésique (On crée le filtre  pour prendre l'image sans la clé)
        int[][] ouvert = MorphoElementaire.ouverture(seuil160, 15);
        // Reconstruction géodésique (On crée l'image sans clé)
        int[][] recon = MorphoComplexe.reconstructionGeodesique(seuil160, ouvert);
        // Soustraction de l'image avec clé par l'image sans clé pour isoler la clé
        int[][] cle = soustraction(seuil160, recon);
        // Fusion de la clé avec l'image où la clé n'est plus visible
        int[][] outils = addition(seuil220, cle);
        // Ouverture 3 (pour retirer les inpuretés)
        int[][] nettoye = MorphoElementaire.ouverture(outils, 3);
        Map<String,int[][]> res = new HashMap<>();
        res.put("outils",nettoye);
        return res;
        

    }
    
    public static Map<String,CImageRGB> application6(int[][] imageVaisseaux, CImageRGB vaisseauxRGB, CImageRGB planeteRGB) 
        throws CImageRGBException, CImageNGException
    {
        // Seuillage initial pour isoler les vaisseaux
        int[][] seuil60 = Seuillage.seuillageSimple(imageVaisseaux, 60);

        // Érosion forte pour ne garder que le gros vaisseau
        int[][] eroded = MorphoElementaire.erosion(seuil60, 21);

        // Reconstruction du gros vaisseau
        int[][] recon = MorphoComplexe.reconstructionGeodesique(eroded, seuil60);

        // Soustraction pour obtenir le petit vaisseau
        int[][] petitVaisseau = soustraction(seuil60, recon);

        // Nettoyage du petit vaisseau
        int[][] opened = MorphoElementaire.ouverture(petitVaisseau, 13);

        // Reconstruction sur **imageVaisseaux** pour un masque complet
        int[][] finalMask = MorphoComplexe.reconstructionGeodesique(opened, imageVaisseaux);

        // Fermeture plus forte pour combler les trous
        int[][] masquePlein = MorphoElementaire.fermeture(finalMask, 9);
        masquePlein = Seuillage.seuillageSimple(masquePlein, 30);

        // Détection des contours avec gradient 
        int[][] contour = ContoursNonLineaire.gradientBeucher(masquePlein);
        contour = Seuillage.seuillageSimple(contour, 1);
        contour = MorphoElementaire.dilatation(contour, 3); // pour l'épaissir un peu

        // Copier le vaisseau sur la planète
        CImageRGB synthese = copierVaisseauSimple(planeteRGB, vaisseauxRGB, masquePlein);

        // Ajouter le contour rouge propre
        CImageRGB synthese2 = ajouterContourRouge(synthese, contour);

        Map<String,CImageRGB> res = new HashMap<>();
        res.put("SYN1", synthese);
        res.put("SYN2", synthese2);
        return res;
    }

    private static CImageRGB ajouterContourRouge(CImageRGB image, int[][] contour) throws CImageRGBException {
        int l = image.getLargeur();
        int h = image.getHauteur();
        int[][] r = new int[l][h];
        int[][] g = new int[l][h];
        int[][] b = new int[l][h];
        image.getMatricesRGB(r, g, b);

        // Parcourir et appliquer le contour rouge
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                if (contour[i][j] > 0) {
                    // Appliquer un rouge pur pour le contour
                    r[i][j] = 255;
                    g[i][j] = 0;
                    b[i][j] = 0;
                }
            }
        }

        return new CImageRGB(r, g, b);
    }
    
    private static CImageRGB copierVaisseauSimple(CImageRGB planete, CImageRGB vaisseau, int[][] masque) 
        throws CImageRGBException {
        int l = planete.getLargeur();
        int h = planete.getHauteur();

        int[][] rP = new int[l][h];
        int[][] gP = new int[l][h];
        int[][] bP = new int[l][h];
        planete.getMatricesRGB(rP, gP, bP);

        int[][] rV = new int[l][h];
        int[][] gV = new int[l][h];
        int[][] bV = new int[l][h];
        vaisseau.getMatricesRGB(rV, gV, bV);

        // Copie simple : si le masque > 0, prendre le vaisseau
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < h; j++) {
                if (masque[i][j] > 0) {
                    rP[i][j] = rV[i][j];
                    gP[i][j] = gV[i][j];
                    bP[i][j] = bV[i][j];
                }
            }
        }

        return new CImageRGB(rP, gP, bP);
    }

    
    public static CImageRGB transformationNG_RGB(int[][] imageNG, String couleur) throws CImageRGBException {
        int width = imageNG.length;
        int height = imageNG[0].length;
        
        int[][] red = new int[width][height];
        int[][] green = new int[width][height];
        int[][] blue = new int[width][height];
        
        switch (couleur.toLowerCase()) {
            case "red" -> red = imageNG;
            case "green" -> green = imageNG;
            case "blue" -> blue = imageNG;
        }
        return new CImageRGB(red, green, blue);
    }
    
    
    public static Map<String,CImageRGB> application7(int[][] image, CImageRGB imageRGB) throws CImageRGBException
    {
        int[][] beucher = ContoursNonLineaire.gradientBeucher(image);
        int[][] seuillage = Seuillage.seuillageDouble(beucher, 70, 90);
        int[][] dilatation = MorphoElementaire.dilatation(seuillage, 2);
        CImageRGB contoursVerts = transformationNG_RGB(dilatation, "green");
        CImageRGB tartineEntouree = additionRGB(imageRGB, contoursVerts);
        Map<String,CImageRGB> res = new HashMap<>();
        res.put("Tartines",tartineEntouree);
        return res;
    }
       public static CImageRGB additionRGB(CImageRGB image1, CImageRGB image2) throws CImageRGBException {
        int width = image1.getLargeur();
        int height = image1.getHauteur();

        if (width != image2.getLargeur() || height != image2.getHauteur()) {
            throw new IllegalArgumentException("Les images doivent avoir la même dimension");
        }

        int[][] red1 = new int[width][height];
        int[][] green1 = new int[width][height];
        int[][] blue1 = new int[width][height];

        int[][] red2 = new int[width][height];
        int[][] green2 = new int[width][height];
        int[][] blue2 = new int[width][height];

        image1.getMatricesRGB(red1, green1, blue1);
        image2.getMatricesRGB(red2, green2, blue2);

        int[][] redResult = new int[width][height];
        int[][] greenResult = new int[width][height];
        int[][] blueResult = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                redResult[i][j] = Math.min(255, red1[i][j] + red2[i][j]);
                greenResult[i][j] = Math.min(255, green1[i][j] + green2[i][j]);
                blueResult[i][j] = Math.min(255, blue1[i][j] + blue2[i][j]);
            }
        }

        return new CImageRGB(redResult, greenResult, blueResult);
    }
    
}