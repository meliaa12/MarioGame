package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;

public class Personnage {

    //**** VARIABLES ****//
    private float largeur, hauteur; // Dimensions du personnage
    private float x, y; // Position du personnage
    private boolean marche; // Vrai quand le personnage marche
    private boolean versDroite; // Vrai quand le personnage est tourné vers la droite
    private int compteur; // Compteur des pas du personnage

    private Texture imgArretDroite; // Texture pour l'arrêt face à droite
    private Texture imgArretGauche; // Texture pour l'arrêt face à gauche
    private Texture imgMarcheDroite; // Texture pour la marche vers la droite
    private Texture imgMarcheGauche; // Texture pour la marche vers la gauche

    //**** CONSTRUCTEUR ****//
    public Personnage(float x, float y, float largeur, float hauteur, String nom) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.compteur = 0;
        this.marche = false;
        this.versDroite = true;

        // Chargement des textures
        this.imgArretDroite = new Texture("images/" + nom + "ArretDroite.png");
        this.imgArretGauche = new Texture("images/" + nom + "ArretGauche.png");
        this.imgMarcheDroite = new Texture("images/" + nom + "MarcheDroite.png");
        this.imgMarcheGauche = new Texture("images/" + nom + "MarcheGauche.png");
    }

    //**** GETTERS ****//
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLargeur() {
        return largeur;
    }

    public float getHauteur() {
        return hauteur;
    }

    public boolean isMarche() {
        return marche;
    }

    public boolean isVersDroite() {
        return versDroite;
    }

    public int getCompteur() {
        return compteur;
    }

    public Texture getTexture() {
        // Retourne la texture appropriée en fonction de l'état du personnage
        if (!marche) {
            return versDroite ? imgArretDroite : imgArretGauche;
        }
        return compteur % 2 == 0 ? 
               (versDroite ? imgMarcheDroite : imgMarcheGauche) : 
               (versDroite ? imgArretDroite : imgArretGauche);
    }

    //**** SETTERS ****//
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setMarche(boolean marche) {
        this.marche = marche;
    }

    public void setVersDroite(boolean versDroite) {
        this.versDroite = versDroite;
    }

    public void setCompteur(int compteur) {
        this.compteur = compteur;
    }

    //**** MÉTHODES ****//

    /**
     * Met à jour le compteur pour animer la marche.
     */
    public void updateMarche(int frequence) {
        if (marche) {
            compteur++;
            if (compteur >= 2 * frequence) {
                compteur = 0;
            }
        }
    }

    /**
     * Libère les ressources utilisées par les textures.
     */
    public void dispose() {
        imgArretDroite.dispose();
        imgArretGauche.dispose();
        imgMarcheDroite.dispose();
        imgMarcheGauche.dispose();
    }
}
