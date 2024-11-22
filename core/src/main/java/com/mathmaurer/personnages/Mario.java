package com.mathmaurer.personnages;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Mario extends Personnage {
    private boolean isJumping = false;
    private int vy = 0;
    private static final int GRAVITY = -1; // Gravité qui affecte la vitesse verticale
    private static final int JUMP_STRENGTH = 15; // Force du saut

    public Mario(float x, float y, float largeur, float hauteur) {
        super(x, y, largeur, hauteur);
    }

    @Override
    public void dessine(SpriteBatch batch) {
        String imagePath;

        if (isJumping) {
            if (versDroite) {
                imagePath = "images/marioSautDroite.png";
            } else {
                imagePath = "images/marioSautGauche.png";
            }
        } else if (marche) {
            compteur++;
            if (versDroite) {
                if (compteur / 5 % 2 == 0) {
                    imagePath = "images/marioMarcheDroite.png";
                } else {
                    imagePath = "images/marioArretDroite.png";
                }
            } else {
                if (compteur / 5 % 2 == 0) {
                    imagePath = "images/marioMarcheGauche.png";
                } else {
                    imagePath = "images/marioArretGauche.png";
                }
            }
        } else {
            if (versDroite) {
                imagePath = "images/marioArretDroite.png";
            } else {
                imagePath = "images/marioArretGauche.png";
            }
        }

        try {
            Texture texture = new Texture(imagePath);
            batch.draw(texture, getX(), getY(), getLargeur(), getHauteur());
        } catch (Exception e) {
            System.err.println("Erreur de chargement de l'image : " + imagePath);
            Texture texture = new Texture("images/default.png");
            batch.draw(texture, getX(), getY(), getLargeur(), getHauteur());
        }
    }

    public void update(int hauteurSol) {
        if (isJumping) {
            vy += GRAVITY; // Appliquer la gravité
            setY(getY() + vy); // Mettre à jour la position verticale

            if (getY() <= hauteurSol) {
                setY(hauteurSol); // Mario touche le sol
                isJumping = false;
                vy = 0;
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
        } else if (getY() > hauteurPlafond) {
            setY(hauteurPlafond);
        }
    }
}