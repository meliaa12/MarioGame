package com.mathmaurer.objets;

public class Objet {
    //**** VARIABLES ****//
    private float largeur, hauteur; // Dimensions de l'objet
    private float x, y; // Position de l'objet

    //**** CONSTRUCTEUR ****//
    public Objet(float x, float y, float largeur, float hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
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

    //**** SETTERS ****//
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(float hauteur) {
        this.hauteur = hauteur;
    }

    //**** METHODES ****//
    
    /** Vérifie si cet objet entre en collision avec un autre */
    public boolean isCollidingWith(Objet other) {
        return this.x < other.x + other.largeur &&
               this.x + this.largeur > other.x &&
               this.y < other.y + other.hauteur &&
               this.y + this.hauteur > other.y;
    }

    /** Libère les ressources (à surcharger dans les sous-classes si nécessaire) */
    public void dispose() {
        // Par défaut, cette méthode ne fait rien.
        // Les sous-classes peuvent la surcharger pour libérer les ressources si nécessaire.
    }
}
