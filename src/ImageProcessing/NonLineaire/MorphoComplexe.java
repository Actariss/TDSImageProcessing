/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ImageProcessing.NonLineaire;

import java.util.*;

/**
 *
 * @author lione
 */
public class MorphoComplexe {

    /**
     * Réalise la dilatation géodésique d'une image selon un masque.
     * @param image L'image à dilater (binaire).
     * @param masqueGeodesique Le masque de conditionnement (binaire).
     * @param nbIter Le nombre d'itérations de dilatation à effectuer (>= 1).
     * @return L'image dilatée géodésiquement.
     */
    public static int[][] dilatationGeodesique(int[][] image, int[][] masqueGeodesique, int nbIter) {
    int largeur = image.length;
    int hauteur = image[0].length;
    int[][] resultat = new int[largeur][hauteur];
    
    for (int i = 0; i < largeur; i++) {
        for (int j = 0; j < hauteur; j++) {
            resultat[i][j] = image[i][j];
        }
    }

    for (int iter = 0; iter < nbIter; iter++) {
        int[][] dilate = MorphoElementaire.dilatation(resultat, 3); // masque 3x3 par défaut
        // Contraindre par le masque géodésique (pixel à pixel : min)
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                resultat[x][y] = Math.min(dilate[x][y], masqueGeodesique[x][y]); 
                // ici (audessus) on compare le pixel obtenu avec le pixel au même emplacement dans le masque, si il y a un pixel noir, c'est lui que l'on garde (LionelGOAT)
            }
        }
    }

    return resultat;
    }

    /**
     * Réalise la reconstruction géodésique d'une image selon un masque.
     * @param image L'image à reconstruire (binaire).
     * @param masqueGeodesique Le masque de conditionnement (binaire).
     * @return L'image reconstruite géodésiquement.
     */
    public static int[][] reconstructionGeodesique(int[][] image, int[][] masqueGeodesique) {
    int width = image.length;
    int height = image[0].length;

    int[][] precedente = new int[width][height];
    int[][] courante = new int[width][height];

    // Copie initiale de l'image dans "precedente"
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            precedente[i][j] = image[i][j];
        }
    }

    boolean changed;

    do {
        changed = false;
        // Une itération de dilatation géodésique
        courante = dilatationGeodesique(precedente, masqueGeodesique, 1);

        // Comparaison entre precedente et courante
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (courante[i][j] != precedente[i][j]) {
                    changed = true;
                    break; // Il suffit de détecter un changement
                }
            }
            if (changed) break;
        }

        // Copier courante dans precedente si changement
        if (changed) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                precedente[i][j] = courante[i][j];
                }
            }
        }

    } while (changed);

    return courante;
    }
    /**
     * Réalise un filtrage médian sur une image en niveaux de gris.
     * @param image L'image à filtrer.
     * @param tailleMasque La taille (impair) du masque utilisé.
     * @return L'image filtrée.
     */
    public static int[][] filtreMedian(int[][] image, int tailleMasque) {
        int largeur = image.length;
        int hauteur = image[0].length;
        int[][] resultat = new int[largeur][hauteur];

        int rayon = tailleMasque / 2;

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                List<Integer> valeurs = new ArrayList<>();

                // Parcours du voisinage
                for (int dx = -rayon; dx <= rayon; dx++) {
                    for (int dy = -rayon; dy <= rayon; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;

                        // Vérifie que le voisin est dans l'image
                        if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur) {
                            valeurs.add(image[nx][ny]);
                        }
                    }
                }

                // Trie et calcule la médiane
                Collections.sort(valeurs);
                int mediane = valeurs.get(valeurs.size() / 2);
                resultat[x][y] = mediane;
            }
        }

        return resultat;
        }

}
