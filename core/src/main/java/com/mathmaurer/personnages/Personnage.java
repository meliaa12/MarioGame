package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.jeu.Main;
import com.mathmaurer.objets.Objet;

public class Personnage {

    //**** VARIABLES ****//
    private float largeur, hauteur; // Dimensions du personnage
    private float x, y; // Position du personnage
    protected boolean marche; // Vrai quand le personnage marche
    protected boolean versDroite; // Vrai quand le personnage est tourné vers la droite
    public int compteur; // Compteur des pas du personnage
    protected boolean vivant; // Vrai si le personnage est vivant
    protected Texture textureMarioArretDroite, textureMarioArretGauche, textureMarioMarcheDroite, textureMarioMarcheGauche;

    //**** CONSTRUCTEUR ****//
    public Personnage(float x, float y, float largeur, float hauteur){
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.compteur = 0;
        this.marche = false;
        this.versDroite = true;
        this.vivant = true;

        // Charger les textures
        textureMarioArretDroite = new Texture("images/marioArretDroite.png");
        textureMarioArretGauche = new Texture("images/marioArretGauche.png");
        textureMarioMarcheDroite = new Texture("images/marioMarcheDroite.png");
        textureMarioMarcheGauche = new Texture("images/marioMarcheGauche.png");
    }

    //**** GETTERS ****//
    public float getX() { return x; }
    public float getY() { return y; }
    public float getLargeur() { return largeur; }
    public float getHauteur() { return hauteur; }
    public boolean isMarche() { return marche; }
    public boolean isVersDroite() { return versDroite; }
    public int getCompteur() { return compteur; }
    public boolean isVivant() { return vivant; }

    //**** SETTERS ****//
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setMarche(boolean marche) { this.marche = marche; }
    public void setVersDroite(boolean versDroite) { this.versDroite = versDroite; }
    public void setCompteur(int compteur) { this.compteur = compteur; }
    public void setVivant(boolean vivant) { this.vivant = vivant; }

    //**** METHODES ****//

    // Méthode pour afficher l'animation du personnage (mouvement ou arrêt)
    public void dessine(SpriteBatch batch) {
        if (!marche) {
            if (versDroite) {
                batch.draw(textureMarioArretDroite, x, y, largeur, hauteur);
            } else {
                batch.draw(textureMarioArretGauche, x, y, largeur, hauteur);
            }
        } else {
            compteur++;
            if (compteur / 5 % 2 == 0) {  // Utilisation d'un intervalle pour l'animation
                if (versDroite) {
                    batch.draw(textureMarioMarcheDroite, x, y, largeur, hauteur);
                } else {
                    batch.draw(textureMarioMarcheGauche, x, y, largeur, hauteur);
                }
            } else {
                if (versDroite) {
                    batch.draw(textureMarioArretDroite, x, y, largeur, hauteur);
                } else {
                    batch.draw(textureMarioArretGauche, x, y, largeur, hauteur);
                }
            }
            if (compteur == 10) {  // Réinitialiser le compteur pour l'animation
                compteur = 0;
            }
        }
    }

    // Déplacement du personnage
    public void deplacement() {
        if (Main.scene.getxPos() >= 0) {
            this.x = this.x - Main.scene.getDx();
        }
    }

    // Détection de collision avant
    public boolean contactAvant(Objet objet) {
        return !(this.x + this.largeur < objet.getX() || this.x + this.largeur > objet.getX() + 5 ||
                this.y + this.hauteur <= objet.getY() || this.y >= objet.getY() + objet.getHauteur());
    }

    // Détection de collision arrière
    public boolean contactArriere(Objet objet) {
        return !(this.x > objet.getX() + objet.getLargeur() || this.x + this.largeur < objet.getX() + objet.getLargeur() - 5 ||
                this.y + this.hauteur <= objet.getY() || this.y >= objet.getY() + objet.getHauteur());
    }

    // Détection de collision sous
    public boolean contactDessous(Objet objet) {
        return !(this.x + this.largeur < objet.getX() + 5 || this.x > objet.getX() + objet.getLargeur() - 5 ||
                this.y + this.hauteur < objet.getY() || this.y + this.hauteur > objet.getY() + 5);
    }

    // Détection de collision dessus
    public boolean contactDessus(Objet objet) {
        return !(this.x + this.largeur < objet.getX() + 5 || this.x > objet.getX() + objet.getLargeur() - 5 ||
                this.y < objet.getY() + objet.getHauteur() || this.y > objet.getY() + objet.getHauteur() + 5);
    }

    public boolean proche(Objet objet) {
        return (this.x > objet.getX() - 10 && this.x < objet.getX() + objet.getLargeur() + 10) ||
                (this.x + this.largeur > objet.getX() - 10 && this.x + this.largeur < objet.getX() + objet.getLargeur() + 10);
    }

    // Détection de contact avec un autre personnage
    public boolean contactAvant(Personnage personnage) {
        if (this.isVersDroite()) {
            return !(this.x + this.largeur < personnage.getX() || this.x + this.largeur > personnage.getX() + 5 ||
                    this.y + this.hauteur <= personnage.getY() || this.y >= personnage.getY() + personnage.getHauteur());
        } else {
            return false;
        }
    }

    public boolean contactArriere(Personnage personnage) {
        return !(this.x > personnage.getX() + personnage.getLargeur() || this.x + this.largeur < personnage.getX() + personnage.getLargeur() - 5 ||
                this.y + this.hauteur <= personnage.getY() || this.y >= personnage.getY() + personnage.getHauteur());
    }

    public boolean contactDessous(Personnage personnage) {
        return !(this.x + this.largeur < personnage.getX() || this.x > personnage.getX() + personnage.getLargeur() ||
                this.y + this.hauteur < personnage.getY() || this.y + this.hauteur > personnage.getY());
    }

    public boolean contactDessus(Personnage personnage) {
        return !(this.x + this.largeur < personnage.getX() || this.x > personnage.getX() + personnage.getLargeur() ||
                this.y < personnage.getY() + personnage.getHauteur() || this.y > personnage.getY() + personnage.getHauteur() + 5);
    }
    public boolean proche(Personnage personnage) {
        return (this.x > personnage.getX() - 10 && this.x < personnage.getX() + personnage.getLargeur() + 10) ||
                (this.x + this.largeur > personnage.getX() - 10 && this.x + this.largeur < personnage.getX() + personnage.getLargeur() + 10);
    }
}