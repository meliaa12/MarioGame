package com.mathmaurer.personnages;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathmaurer.objets.Objet;

public class Mario extends Personnage {
    private Texture textureMarioArretDroite;
    private Texture textureMarioArretGauche;
    private Texture textureMarioMarcheDroite;
    private Texture textureMarioMarcheGauche;
    private Texture textureMarioSautDroite;
    private Texture textureMarioSautGauche;
    private Texture textureMarioMeurt;
    private int compteurMort;
    private boolean isJumping;
    private float vy;
    private static final float GRAVITY = -0.5f;
    private static final float JUMP_STRENGTH = 10f;

    public Mario(float x, float y, float largeur, float hauteur) {
        super(x, y, largeur, hauteur);
        this.textureMarioArretDroite = new Texture("images/marioArretDroite.png");
        this.textureMarioArretGauche = new Texture("images/marioArretGauche.png");
        this.textureMarioMarcheDroite = new Texture("images/marioMarcheDroite.png");
        this.textureMarioMarcheGauche = new Texture("images/marioMarcheGauche.png");
        this.textureMarioSautDroite = new Texture("images/marioSautDroite.png");
        this.textureMarioSautGauche = new Texture("images/marioSautGauche.png");
        this.textureMarioMeurt = new Texture("images/marioMeurt.png");
        this.compteurMort = 0;
        this.isJumping = false;
        this.vy = 0;
    }

    public void dessine(SpriteBatch batch) {
        Texture texture;
        if (!isVivant()) {
            texture = textureMarioMeurt;
        } else if (isJumping) {
            if (versDroite) {
                texture = textureMarioSautDroite;
            } else {
                texture = textureMarioSautGauche;
            }
        } else if (marche) {
            if (versDroite) {
                texture = textureMarioMarcheDroite;
            } else {
                texture = textureMarioMarcheGauche;
            }
        } else {
            if (versDroite) {
                texture = textureMarioArretDroite;
            } else {
                texture = textureMarioArretGauche;
            }
        }

        batch.draw(texture, getX(), getY(), getLargeur(), getHauteur());
    }

    public void update(int hauteurSol, List<Objet> objets) {
        if (isJumping) {
            vy += GRAVITY; // Appliquer la gravité
            setY(getY() + vy); // Mettre à jour la position verticale

            for (Objet objet : objets) {
                if (contactDessous(objet)) {
                    setY(objet.getY() + objet.getHauteur());
                    isJumping = false;
                    vy = 0;
                    break;
                }
            }

            if (getY() <= hauteurSol) {
                setY(hauteurSol); // Mario touche le sol
                isJumping = false;
                vy = 0;
            }
        } else {
            boolean onGround = false;
            for (Objet objet : objets) {
                if (contactDessus(objet)) {
                    setY(objet.getY() + objet.getHauteur());
                    onGround = true;
                    break;
                }
            }
            if (!onGround && getY() > hauteurSol) {
                isJumping = true;
            }
        }
    }

    public void jump(int hauteurSol) {
        if (!isJumping) {
            isJumping = true;
            vy = JUMP_STRENGTH; // Appliquer la force du saut
        }
    }

    public void climb(boolean up, int hauteurSol, int hauteurPlafond) {
        if (up) {
            setY(getY() + 5);
        } else {
            setY(getY() - 5);
        }

        if (getY() < hauteurSol) {
            setY(hauteurSol);
        }
        if (getY() > hauteurPlafond) {
            setY(hauteurPlafond);
        }
    }

    public void meurt() {
        String str = "images/boom.png";
        this.compteurMort++;
        if (this.compteurMort > 5) {
            str = "images/marioMeurt.png";
            this.setY(this.getY() - 0);
            this.setVivant(false);
        }
        this.textureMarioMeurt = new Texture(str);

       
       
    }

    // Nouvelle méthode pour définir la vitesse verticale
    public void setVelocityY(float velocity) {
        // Utilisé pour le petit saut après avoir écrasé un ennemi
        this.vy = velocity;
    }

     // Nouvelle méthode pour définir l'état de saut
     public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }
}